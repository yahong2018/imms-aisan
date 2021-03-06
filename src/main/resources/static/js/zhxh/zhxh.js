function isLogined(){
    var authentication_data = sessionStorage.getItem("authentication_data");
    if(!authentication_data){
        console.log("Not login, redirecting to /front-login");
        window.location.href="front-login";
        return false;
    }
    return true;
}

function banBackSpace(e) {
    var ev = e || window.event;
    var obj = ev.target || ev.srcElement;
    var t = obj.type || obj.getAttribute('type');
    var vReadOnly = obj.getAttribute('readonly');
    var vEnabled = obj.getAttribute('enabled');
    vReadOnly = (vReadOnly == null) ? false : vReadOnly;
    vEnabled = (vEnabled == null) ? true : vEnabled;

    var flag1 = (ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea")
        && (vReadOnly != false || vEnabled != true)) ? true : false;

    var flag2 = (ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")
        ? true : false;

    if (flag2 || flag1) {
        return false;
    }
}

if(isLogined()) {
    //禁止后退键 作用于Firefox、Opera
    document.onkeypress = banBackSpace;
    //禁止后退键 作用于IE、Chrome
    document.onkeydown = banBackSpace;
}