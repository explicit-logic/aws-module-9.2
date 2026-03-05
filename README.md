# Module 9 - AWS Services

This repository contains a demo project created as part of my **DevOps studies** in the **TechWorld with Nana – DevOps Bootcamp**.

https://www.techworld-with-nana.com/devops-bootcamp

***Demo Project:*** CD - Deploy Application from Jenkins Pipeline to EC2 Instance (automatically with docker)

***Technologies used:*** AWS, Jenkins, Docker, Linux, Git, Java, Maven, Docker Hub

***Project Description:*** 

- Prepare AWS EC2 Instance for deployment (Install Docker)
- Create ssh key credentials for EC2 server on Jenkins
- Extend the previous CI pipeline with deploy step to ssh into the remote EC2 instance and deploy newly built image from Jenkins server
- Configure security group on EC2 Instance to allow access to our web application

---

## Prerequisites

> **Complete the previous demo project first.**
> The EC2 instance must be launched and has installed docker
> [aws-module-9.1](https://github.com/explicit-logic/aws-module-9.1)

> Login to docker `docker login`

---

### Install Jenkins plugin

- Open Jenkins, Navigate to `Manage Jenkins` -> `Plugins`

- Install `SSH Agent`

### Configure a Multibranch Pipeline

1. Go to **Dashboard** → **New Item**
2. Name it `aws-multibranch`, select **Multibranch Pipeline**, click **OK**

**Branch Sources:**
- Click **Add source** → **GitHub**

| Field | Value |
|---|---|
| Credentials | `github` |
| Repository HTTPS URL | `https://github.com/explicit-logic/aws-module-9.2` |

- Click **Validate** to confirm access

**Behaviors** — click **Add** and include:
- `Discover branches`
- `Discover pull requests from origin`

**Build Configuration:**
- Script Path: `Jenkinsfile`

**Scan Multibranch Pipeline Triggers:**
- Check **Periodically if not otherwise run** → set interval to `1 day`

3. Click **Save** — Jenkins will scan the repository and create jobs for each branch


### Create ssh key credentials for EC2 server on Jenkins

1. Navigate to `aws-multibranch` pipeline -> Credentials

2. Add Credentials

Kind: `SSH Username with private key`

ID: `aws-ec2`

Username: `ec2-user`

Private Key: `<copy the content of the pem file used for ssh connection to EC2 instance>` (`cat ~/.ssh/app-key.pem`)

3. Generate code snippet for the `ssh-agent`

```groovy
sshagent(['aws-ec2']) {
    // some block
}
```

![](./images/snippet.png)

See the full code at `app/script.groovy` (`deployApp`)

4. Add Jenkins IP address to firewall rules on the EC2 instance


Edit inbound rules -> Add rule

Type: SSH

Source: `<Jenkins-IP>/32`

> Add custom port `8080` as well

![](./images/firewall.png)


