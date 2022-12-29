
<!-- PROJECT SHIELDS -->
<a name="readme-top"></a>
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Main Branch](https://github.com/tekcapsule/capsule-service/actions/workflows/cicd.yml/badge.svg)](https://github.com/tekcapsule/capsule-service/actions/workflows/cicd.yml) 
[![SonarCloud Bugs](https://sonarcloud.io/api/project_badges/measure?project=tekcapsule_capsule-service&metric=bugs)](https://sonarcloud.io/project/overview?id=tekcapsule_capsule-service)
[![SonarCloud Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=tekcapsule_capsule-service&metric=vulnerabilities)](https://sonarcloud.io/project/overview?id=tekcapsule_capsule-service)
[![SonarCloud Code Smell](https://sonarcloud.io/api/project_badges/measure?project=tekcapsule_capsule-service&metric=code_smells)](https://sonarcloud.io/project/overview?id=tekcapsule_capsule-service)
[![SonarCloud Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=tekcapsule_capsule-service&metric=ncloc)](https://sonarcloud.io/project/overview?id=tekcapsule_capsule-service)
[![Github all releases](https://img.shields.io/github/downloads/tekcapsule/capsule-service/total.svg)](https://GitHub.com/tekcapsule/capsule-service/releases/)


<!-- PROJECT LOGO -->

<div align="center">
  <img src="https://user-images.githubusercontent.com/9839481/209770761-99fd940f-3c75-407b-a339-9fd2cd2c5b7f.svg">
  <h3 align="center">Capsule Service</h3>
  <p align="center">
    A set of lambda functions to manage capsules in TekCapsule
    <br />
    <a href="https://www.tekcapsule.com/">View Demo</a> |
    <a href="https://github.com/tekcapsule/capsule-service/issues">Report Bug</a> |
    <a href="https://github.com/tekcapsule/capsule-service/issues">Request Feature</a>
  </p>
</div>
<div align="center">
  
  <a href="https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=389065158&machine=standardLinux32gb&location=SouthEastAsia">![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)</a>

</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
  </ol>
</details>


<!-- ABOUT THE PROJECT -->
## About The Project

This project contains set of functions written in Java 11 to create, update, query and manage TekCapsule capsules. It is a multi-module maven project with two modules named application & domain. Application module is a springboot application that exposes these functions and domain module contains the domain logic.

![capsule-service](https://user-images.githubusercontent.com/9839481/209629340-95671e48-48f8-4173-aa88-e6920a9122a0.png)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
* ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
* ![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)

<p align="right">(<a href="#readme-top">back to top</a>)</p>
<!-- GETTING STARTED -->

## Getting Started

Please follow the below instructions to setup the project locally.

### Prerequisites

This project requires following softwares: 
* SAM  
* JAVA 11
* Maven 

### Installation

Please follow the step by step instructions to install the required softwares and setup the project.
1. Get the AWS Access Key details
2. Clone the repo
   ```sh
   git clone https://github.com/tekcapsule/capsule-service.git
   ```
3. Install SAM CLI by following instructions https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/install-sam-cli.html
4. Configure the following Environment variables
* APPLICATION_ENVIRONMENT=<Environment_Name>
* CLOUD_REGION=<Region_Name>
* secrets.AWS_ACCESS_KEY_ID=<Access_KEY_ID>
* secrets.AWS_SECRET_ACCESS_KEY=<Access_KEY_Value>
* secrets.SERVER_PASSWORD=<SERVER_PASSWORD>
* secrets.SERVER_USERNAME=<SERVER_USERNAME>

Note: SERVER_USERNAME and SERVER_PASSWORD details will be be provided by the maintainers on request.

5. Copy the settings.xml to ~/.m2
6. Run the command
   ```sh
   sam build
   ```
   
<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/tekcapsule/capsule-service/issues) for a list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTRIBUTING -->
## How to Contribute?

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/Neweature`)
3. Commit your Changes (`git commit -m 'Add some NewFeature'`)
4. Push to the Branch (`git push origin feature/NewFeature`)
5. Open a Pull Request

## Contributors
<img src= "https://contrib.rocks/image?repo=tekcapsule/capsule-service">

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- LICENSE -->
## License

Distributed under the GNU GENERAL PUBLIC LICENSE. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

