<h2>Code Setup</h2>

<strong><i>docker-compose</i></strong> file contains 2 container configuration(redis and postgres)<br>
<strong><i>dbschema.sql</i></strong> contain the schema

Run the following command to up docker container:<br> 
  <code>sudo docker-compose up -d</code><br/>
  <strong>Note:</strong> When you run the above command first time, it will build/configure all the containers(along with pulling the schema file and setup the database) and start it all.<br/>
  
You will able to see all the running containers(mypostgresql1 and myredis1) by running the following command<br>
  <code>sudo docker ps</code>

Run the following command to down all docker containera:<br> 
    <code>sudo docker-compose down</code><br/>
    
<h3>Running with IDE</h3>
  Do <i>maven clean install</i> operation and simply run the applications as spring boot,
  you will able to access app on port <code>8080</code>, access <code>http://localhost:8080/rest/test</code> on your browser.

<h3>Connect to postgres</h3>
IP: <code>176.21.2.2:5432</code><br/>
DB name: <code>testdemo</code><br/>
username <code>postgres</code><br/>
password: <code>postgress</code>

To run fetch date from cosumer use:
<code>sudo docker exec --interactive --tty broker \
kafka-console-consumer --bootstrap-server broker:9092 \
--topic firstTime \
--from-beginning</code>