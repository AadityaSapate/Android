<?php
error_reporting(0);
$error = "";
$result = "";
if($_POST){
       
             $link = mysqli_connect("localhost", "id4262232_aditya", "adityasapate","id4262232_email");                 
             if(mysqli_connect_error())
             {
                 
                 echo "Not Connected to database";
                 
             }
             
             $query = "INSERT INTO `email`(`sender`, `receiver`, `subject`, `message`, `count`) VALUES ('".mysqli_real_escape_string($link, $_POST['from'])."','".mysqli_real_escape_string($link, $_POST['to'])."','".mysqli_real_escape_string($link, $_POST['subject'])."','".mysqli_real_escape_string($link, $_POST['msg'])."','".count($_FILES['files']['tmp_name'])."')";
             
              
             mysqli_query($link, $query);
            
            $query = "SELECT * FROM email ORDER BY id DESC LIMIT 1;";
             $result =  mysqli_query($link, $query);
            
             
             
  if(!$_POST["from"])
  {
       $error .= "senders email address is required.<br>";
      
  }
   if(!$_POST["from"])
  {
       $error .= "receivers email address is required.<br>";
      
  }
  
  
  
 
  
   if ($_POST['from'] && filter_var($_POST["from"], FILTER_VALIDATE_EMAIL) === false) {
            
            $error .= "The email address is invalid.<br>";
            
        }  
  
   if($error != "")
   {  
       echo '<div class="alert alert-danger" role="alert"><p>There were error(s) in your form:</p>' . $error . '</div>';

   }
   else
   {   
       $to = $_POST['to'];
       $subject = $_POST['subject'];
       $message = $_POST['msg'];
       $from = $_POST['from'];
   
    
  
    $separator = md5(time());

   
    $eol = "\r\n";

    // main header 
    $headers = "From:".$from . $eol;
    $headers .= "MIME-Version: 1.0" . $eol;
    $headers .= "Content-Type: multipart/mixed; boundary=\"" . $separator . "\"" . $eol;
    $headers .= "Content-Transfer-Encoding: 7bit" . $eol;
    $headers .= "This is a MIME encoded message." . $eol;

    // message
    $body = "--" . $separator . $eol;
    $body .= "Content-Type: text/plain; charset=\"iso-8859-1\"" . $eol;
    $body .= "Content-Transfer-Encoding: 7bit" . $eol;
    $body .= $message . $eol;


   for($i=0 ; $i<count($_FILES['files']['tmp_name']); $i++)
   
   {  
        $filename = $_FILES["files"]["name"][$i];
       
        $path = $_FILES["files"]["tmp_name"][$i];
        
        $file = $path . "/" . $filename;
           
        $f  = file_get_contents( $_FILES["files"]["tmp_name"][$i]);
       
    
    
    $content = chunk_split(base64_encode($f));
    
  
  


    // attachment
    $body .= "--" . $separator . $eol;
    $body .= "Content-Type: application/octet-stream; name=\"" . $_FILES["files"]["name"][$i] . "\"" . $eol;
    $body .= "Content-Transfer-Encoding: base64" . $eol;
    $body .= "Content-Disposition: attachment" . $eol;
    $body .= $content . $eol;
    }
      $body .= "--" . $separator . "--";  
       
       
       $retval = mail($to,$subject,$body,$headers);
    if(isset($retval))//change
    {
        echo "Message sent successfully...";
    }
    else
    {
        echo "Message could not be sent...";
    }
       
       
       
         
         
   }
}
?>


<html>
  <head>
    <title>mail</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
  </head>
  <body>
    
   
    
            <form id="myform" method="POST" enctype="multipart/form-data">
  
   <div class="form-group">
    <label for="exampleFormControlInput1">To</label>
    <input type="email" class="form-control" name="to" placeholder="name@example.com">
  </div>
  <div class="form-group">
    <label for="exampleFormControlInput1">From</label>
    <input type="email" class="form-control" name="from" placeholder="name@example.com">
  </div>
    
 <div class="form-group">
    <label for="exampleFormControlInput1">subject</label>
    <input type="text" class="form-control" name="subject" id="subject" >
  </div>
 
  <div class="form-group">
    <label for="exampleFormControlTextarea1">Example textarea</label>
    <textarea class="form-control" id="Textarea1" name="msg" rows="3"></textarea>
  </div>
  

    <input type="file" class="form-control" name="files[]" multiple >
   
  <button type="submit" class="btn btn-primary" id="submit" >Submit</button>
  
    
  
</form>
    <div><?php 
         error_reporting(0);
         
           while($row = mysqli_fetch_row($result))
        {
           
            
       echo  '<h3>From : '.$row[1].'</h3><br> <h3>To : '.$row[2].'</h3><br><h3> Subject : '.$row[3].'</h3><br><h3>Message : '.$row[4].'</h3><br><h3> No of Files : '.$row[5] ; 
        }
        ?>  
    </div>
     

     <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
    
    
    
  </body>
</html>