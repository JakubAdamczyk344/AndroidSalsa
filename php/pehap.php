<?php
define('HOST','127.0.0.1');
define('USER','root');
define('PASS','');
define('DB','bazadanychlistview');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
mysqli_set_charset( $con, 'utf8');
 
$sql = "select * from events";
 
$res = mysqli_query($con,$sql);
 
$event = array();
 
while($row = mysqli_fetch_array($res)){
array_push($event,
array('day'=>$row['day'],
'hour'=>$row['hour'],
'city'=>$row['city'],
'cityBlock'=>$row['cityBlock'],
'description'=>$row['description']
));
}
 
echo json_encode(array("event"=>$event));
 
mysqli_close($con);
 
?>