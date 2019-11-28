pipeline {
  agent {
    docker {
      image 'maven:3.6.0-jdk-11:latest'
    }

  }
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