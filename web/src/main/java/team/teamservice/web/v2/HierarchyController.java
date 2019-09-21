package team.teamservice.web.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/v2/hierarchy")
public class HierarchyController
    {

    private final HierarchyRepository repository;

    @Autowired
    public HierarchyController(HierarchyRepository repository)
        {
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

    @RequestMapping("/relatives/{slug}")
    public HierarchyEntity relatives(@PathVariable String slug)
        {
        return repository.findBySlug(slug);
        }

    @RequestMapping("/{slug}")
    public List<Relation> hierarchy(@PathVariable String slug)
        {

        List<Relation> hierarchy = new ArrayList<>();

        List<HierarchyEntity> hierarchyEntities;

        if("all".equals(slug)){
            hierarchyEntities =  repository.findCompleteHierarchy();
        } else {
            hierarchyEntities = repository.findHierarchyBelow(slug);
        }

        for(HierarchyEntity hierarchyEntity : hierarchyEntities){
            Relation rootTemp = hierarchyEntity.getRelation();

            List<Relation> children = findChildren(hierarchyEntity.getSlug(), hierarchyEntity.getChildren());

            Relation root = new Relation(rootTemp.getSlug(), rootTemp.getEntityType(), rootTemp.getName(), rootTemp.getParentSlug(), children);

            hierarchy.add(root);
        }

         return hierarchy;
        }

    @RequestMapping("/all")
    @ResponseBody
    public List<HierarchyEntity> completeHierarchy()
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
