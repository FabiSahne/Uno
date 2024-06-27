# Build Base Image
FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1

# Add Workdir
WORKDIR /Uno
ADD . /Uno

RUN sbt update
CMD sbt run