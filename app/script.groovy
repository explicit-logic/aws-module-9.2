def testApp() {
  echo 'testing the application...'
  sh 'mvn test'
}

def deployApp(Map dockerImage, String ipAddress) {
  echo 'deploying the application...'
  def dockerCmd = "docker run -p 8080:8080 -d ${dockerImage.image}:${dockerImage.tag}"
  sshagent(['aws-ec2']) {
    sh "ssh -o StrictHostKeyChecking=no ec2-user@${ipAddress} ${dockerCmd}"
  }
}

return this
