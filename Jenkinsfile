pipeline {

  // 设置全局变量
  environment {
    // jar 文件的stash名称,maven编译好的jar包需要转移到jdk容器中
    STASH_JAR = "mc_muchen"
    // jar镜像名称,定义jar文件名 ->
    IMAGE_JAR_NAME = "${JOB_NAME}:latest"
  }

  agent none
  stages{

    // 进行maven构建
    stage('Build jar file'){
        agent {
          // 制定docker镜像,jenkins会自动拉取image,运行一个容器
          // 所以需要部署jenkins的服务器事先安装好docker
          docker {
            image 'maven:3-alpine'
          }
        }
        steps {
            // 在镜像中执行的shell命令
            sh 'mvn -DskipTests clean package'
            // 将打包的好的jar文件临时存储起来,我们需要把这个jar文件移动到jdk容器中运行起来
            stash includes: 'target/*.jar', name: "${STASH_JAR}"
        }
    }

    // 构建部署镜像
    stage('Build docker image') {
        agent any

        steps {
            script {
                // 删除之前的镜像,${IMAGE_JAR_NAME}相同不会生成重复的image
                // sh "docker rmi ${IMAGE_JAR_NAME}"

                // 将打包的jar从临时存放的位置拿出来,这就转移到了宿主机中
                unstash "${STASH_JAR}"
                // ${JOB_NAME} -> github-jenkins-demo/master
                // 构建镜像
                def targetImage = docker.build("${IMAGE_JAR_NAME}", ' .')
            }
        }
    }

    // 运行容器
    stage('Deploy docker continer') {
        agent any

        steps {
            script {
                // 运行镜像容器，--rm表示容器kill后不保存,直接删除
                sh "docker run --rm ${IMAGE_JAR_NAME}"
            }
        }
    }
  }
}