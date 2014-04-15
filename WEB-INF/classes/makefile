JFLAGS = -g
JC = 	javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASS_PATH = -classpath ../lib/ojdbc6.jar:../lib/servlet-api-2.3.jar:../lib/commons-fileupload-1.0.jar

SOURCE_PATH = -sourcepath ./

all:
	make login
	make register
	make records
	make images
	make userManagement
	make reports
	make search
	make image

login:
	$(JC) $(SOURCE_PATH) $(CLASS_PATH) UserLoginServlet.java
register:
	$(JC) $(SOURCE_PATH) $(CLASS_PATH) UserRegistrationServlet.java
records:
	$(JC) $(SOURCE_PATH) $(CLASS_PATH) UploadRecordServlet.java
images:
	$(JC) $(SOURCE_PATH) $(CLASS_PATH) GetBigPic.java GetOnePic.java
userManagement:
	$(JC) $(SOURCE_PATH) $(CLASS_PATH) UserManagementServlet.java
reports:
	$(JC) $(SOURCE_PATH) $(CLASS_PATH) ReportServlet.java
search:
	$(JC) $(SOURCE_PATH) $(CLASS_PATH) SearchServlet.java
image:
        $(JC) $(SOURCE_PATH) $(CLASS_PATH) UploadImage.java

clean:
	$(RM) *.class
	$(RM) entities/*.class
