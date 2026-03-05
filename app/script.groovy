def testApp() {
  echo 'testing the application...'
  sh 'mvn test'
}

def deployApp(String image, String tag) {
  echo 'deploying the application...'
  def dockerCmd = "docker run -p 8080:8080 -d ${image}:${tag}"
  sshagent(['aws-ec2']) {
    sh "ssh -o StrictHostKeyChecking=no ec2-user@54.93.40.224 ${dockerCmd}"
  }
}

return this
