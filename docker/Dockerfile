FROM kroger/min-debian


RUN apt-get update && apt-get install -y \
  beanstalkd


EXPOSE 11300
RUN mkdir /beanstalkd
CMD ["/usr/bin/beanstalkd", "-b", "/beanstalkd", "-V", "-l", "localhost", "-p", "11300"]
