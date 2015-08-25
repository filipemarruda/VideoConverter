# VideoConverter
This service is hosted on amazon web services and uses encoding.com video converter service. When you upload your video file this is saved in an arbitrary amazon s3 bucket and a request to the encoding.com converter workers is triggered. This ones will convert your video and put back to the amazon s3 bucket in the mp4 format, ready to be played by any web browser player. After that we will redirect you to a page witch contains a HTML5 video tag pointing to the converted video.

## Configuration instructions
If you decide to use this project, remember to put your credencials(**AWSAccessKey**, **AWSSecretKey**, **EncodingUserId**, **EncodingUserKey**) and S3 bucket(**S3Bucket**) name in the properties file:
> *src/main/resources/Sambatech.properties*

## list of files included
> .classpath
> .gitignore
> .project
> .settings/.jsdtscope
> .settings/org.eclipse.jdt.core.prefs
> .settings/org.eclipse.m2e.core.prefs
> .settings/org.eclipse.wst.common.component
> .settings/org.eclipse.wst.common.project.facet.core.xml
> .settings/org.eclipse.wst.jsdt.ui.superType.container
> .settings/org.eclipse.wst.jsdt.ui.superType.name
> .settings/org.eclipse.wst.validation.prefs
> WebContent/META-INF/MANIFEST.MF
> WebContent/images/loading.gif
> WebContent/index.html
> WebContent/js/bootstrap.min.js
> WebContent/js/jquery-1.11.3.min.js
> WebContent/js/jquery.form.js
> WebContent/js/main.js
> WebContent/styles/bootstrap-theme.min.css
> WebContent/styles/bootstrap.min.css
> WebContent/styles/styles.css
> pom.xml
> src/main/java/com/filipemarruda/aws/S3BucketHandler.java
> src/main/java/com/filipemarruda/bundle/Properties.java
> src/main/java/com/filipemarruda/encoding/EncodingHandler.java
> src/main/java/com/filipemarruda/encoding/MediaWorkflow.java
> src/main/java/com/filipemarruda/file/FileHandler.java
> src/main/java/com/filipemarruda/http/HttpConnector.java
> src/main/java/com/filipemarruda/http/IHttpConnector.java
> src/main/java/com/filipemarruda/http/RequestHelper.java
> src/main/java/com/filipemarruda/http/servlets/Convert.java
> src/main/java/com/filipemarruda/xml/XMLParser.java
> src/main/resources/SambaTech.properties
> src/test/java/com/filipemarruda/MockUtil.java
> src/test/java/com/filipemarruda/aws/S3BucketHandlerTest.java
> src/test/java/com/filipemarruda/bundle/PropertiesTest.java
> src/test/java/com/filipemarruda/encoding/EncodingHandlerTest.java
> src/test/java/com/filipemarruda/encoding/MediaWorkflowTest.java
> src/test/java/com/filipemarruda/file/FileHandlerTest.java
> src/test/java/com/filipemarruda/http/HttpConnectorTest.java
> src/test/java/com/filipemarruda/http/RequestHelperTest.java
> src/test/java/com/filipemarruda/http/SimpleTestPart.java
> src/test/java/com/filipemarruda/http/servlets/ConvertTest.java
> src/test/java/com/filipemarruda/xml/XMLParserTest.java

## Copyright and licensing information
> Copyright (c) 2015, Filipe Mendes
> 
> Video Converter
> 
> This project consists in a simple video converter made in order 
> to the people from SambaTech evaluate my programming skills
> 
> All rights reserved.
> Redistribution and use in source and binary forms, with or 
> without modification, are permitted provided that the following
> conditions are met:
> 
> 1. Redistributions of source code must retain the above 
> copyright notice, this list of conditions and the following 
> disclaimer.
> 
> 2. Redistributions in binary form must reproduce the above 
> copyright notice, this list of conditions and the following 
> disclaimer in the documentation and/or other materials provided 
> with the distribution.

> 3. Neither the name of the copyright holder nor the names of 
> its contributors may be used to endorse or promote products 
> derived from this software without specific prior written 
> permission.

> THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND 
> CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
> INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
> MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
> DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
> CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
> SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
> NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
> LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
> HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
> CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
> OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
> EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
