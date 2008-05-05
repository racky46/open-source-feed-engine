mysql_root_username='root'
mysql_root_password='root'
mysql_url='jdbc:mysql://localhost:3306/'

db_dbname='testfe'
db_url = "jdbc:mysql://localhost:3306/${db_dbname}"
db_username = 'root'
db_password = 'root'
db_driver = 'com.mysql.jdbc.Driver'


def ant = new AntBuilder()

ant.sql(driver:db_driver, url:mysql_url, userid:mysql_root_username, password:mysql_root_password, print:true) {
  transaction("DROP DATABASE IF EXISTS ${db_dbname};")
}
ant.sql(driver:db_driver, url:mysql_url, userid:mysql_root_username, password:mysql_root_password, print:true) {
  transaction("CREATE DATABASE IF NOT EXISTS ${db_dbname};")
  transaction("GRANT ALL ON ${db_dbname}.* TO '${db_username}'@'localhost' IDENTIFIED BY '${db_password}';")
  transaction("GRANT FILE ON *.* TO '${db_username}'@'localhost' IDENTIFIED BY '${db_password}';")
}
ant.sql(driver:db_driver, url:db_url, userid:db_username, password:db_password, print:true, src:'../mysql/schema/ddl/ver1.0/create.ddl')
ant.sql(driver:db_driver, url:db_url, userid:db_username, password:db_password, print:true, src:'../mysql/schema/ddl/ver1.0/populate.sql')











