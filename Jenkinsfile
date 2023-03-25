
node {
  stage("Clone the project") {
    git branch: 'master', url: 'https://github.com/Anton19992208/MyProject'
  }

  stage("Compilation") {
    sh "mvn -version"
  }

  stage("Tests and Deployment") {
    stage("Runing unit tests") {
      sh "./mvnw test -Punit"
    }
    stage("Deployment") {
      sh 'nohup ./mvnw spring-boot:run -Dserver.port=8001 &'
    }
  }
}
