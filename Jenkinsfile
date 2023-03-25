pipeline {
	agent any

	tools{
            maven 'Maven'
	}

	stages {

		stage('Build'){
			steps {
				sh 'mvn clean install -DskipTests'
			}
		}

		stage('Test'){
			steps{
				sh 'mvn test'
			}
		}

		stage('Deploy') {
			steps {
			    sh 'mvn jar:jar deploy:deploy'
			}
		}
	}
}
