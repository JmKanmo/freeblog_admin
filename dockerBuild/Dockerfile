FROM centos:7

RUN yum -y upgrade
RUN yum -y install rdate
RUN yum install -y net-tools
RUN yum install lrzsz -y
RUN yum -y install wget
RUN yum -y install unzip
RUN yum install -y \
    java-11-openjdk
ENV JAVA_HOME /etc/alternatives/jre

EXPOSE 8600 22

ADD jboot.sh /freeblog_admin/jboot.sh
ADD freeblog_admin-1.0.war /freeblog_admin/freeblog_admin-1.0.war

RUN chmod +x /freeblog_admin/jboot.sh

CMD /freeblog_admin/jboot.sh

