var logBtn = document.querySelector('.loginBtn');
var userId = document.querySelector('#userId').value;
var userpw = document.querySelector('#userpw').value;

logBtn.onclick=function() {
    var xhr = new XMLHttpRequest();
    var url = '';

    xhr.open('POST', url, true);
    xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
     if (xhr.readyState == 4 && xhr.status == 200) {
            console.log('Got a POST request');
            window.location.href=("homepage.html");
        }
    }

    xhr.send("studentId="+ userId.value+"&psd="+userpw.value);
}