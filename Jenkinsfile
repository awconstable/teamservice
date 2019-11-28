pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '''#/bin/bash
env'''
        sh 'mvn clean compile'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }

    stage('Deploy') {
      steps {
        sh 'echo "Deploy"'
      }
    }

  }
}