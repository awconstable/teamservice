package team.teamservice.web.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import team.teamservice.web.hierarchy.service.HierarchyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/v2/hierarchy", produces = "application/json")
public class HierarchyController
    {

    private final HierarchyService hierarchyService;
    private final HierarchyRepository repository;

    @Autowired
    public HierarchyController(HierarchyService hierarchyService, HierarchyRepository repository)
        {
        this.hierarchyService = hierarchyService;
        this.repository = repository;
        }

    @RequestMapping("/ancestors/{slug}")
    public List<HierarchyEntity> ancestors(@PathVariable String slug)
        {
        return repository.findAncestors(slug);
        }

    @RequestMapping("/children/{slug}")
    public List<HierarchyEntity> children(@PathVariable String slug)
        {
        return repository.findChildren(slug);
        }

    @RequestMapping("/children/application/ids/{slug}")
    public Collection<String> applicationChildIds(@PathVariable String slug) {
        return hierarchyService.getApplicationHierarchyIds(slug);
    }

    @RequestMapping("/relatives/{slug}")
    public HierarchyEntity relatives(@PathVariable String slug)
        {
        return repository.findBySlug(slug);
        }

    private List<Relation> processHierarchy(List<HierarchyEntity> hierarchyEntities){
        List<Relation> hierarchy = new ArrayList<>();

        for(HierarchyEntity hierarchyEntity : hierarchyEntities){
            Relation rootTemp = hierarchyEntity.getRelation();

            List<Relation> children = findChildren(hierarchyEntity.getSlug(), hierarchyEntity.getChildren());

            Relation root = new Relation(rootTemp.getSlug(), rootTemp.getEntityType(), rootTemp.getName(), rootTemp.getParentSlug(), children);

            hierarchy.add(root);
        }

        return hierarchy;
    }

    @RequestMapping("/complete")
    public List<Relation> completeHierarchy()
        {
        List<HierarchyEntity> hierarchyEntities =  repository.findCompleteHierarchy();

        return processHierarchy(hierarchyEntities);
        }

    @RequestMapping("/complete/{slug}")
    public Relation completeHierarchyBySlug(@PathVariable String slug)
        {
            List<HierarchyEntity> hierarchyEntities = repository.findHierarchyBelow(slug);

            List<Relation> relations = processHierarchy(hierarchyEntities);
            return relations.get(0);
        }

    @RequestMapping("/all")
    @ResponseBody
    public List<HierarchyEntity> findAllHierarchy()
        {
        return repository.findAll();
        }

    private List<Relation> findChildren(String parentSlug, Collection<Relation> children){
        List<Relation> directChildren = new ArrayList<>();
        for(Relation child: children){
            if(parentSlug.equals(child.getParentSlug())){
               List<Relation> grandChildren = findChildren(child.getSlug(), children);
               Relation grandChild = new Relation(child.getSlug(), child.getEntityType(), child.getName(), child.getParentSlug(), grandChildren);
               directChildren.add(grandChild);
            }
        }
        return directChildren;
    }

    }
