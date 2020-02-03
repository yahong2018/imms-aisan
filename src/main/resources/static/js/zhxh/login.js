function handleFailure(response, opts) {
    const message = response.responseText.trim().replace("\n", "<br>");
    console.log(message);
    Ext.MessageBox.show({
        title: '系统提示',
        msg:'用户名或密码错误，请重试。',
        //msg: message,
        buttons: Ext.MessageBox.OK,
        icon: Ext.MessageBox.ERROR
    });
}

function onLogin() {
    const txtUserName = document.getElementById('txtUserName');
    const txtPassword = document.getElementById('txtPassword');
    const txtErrorMessage = document.getElementById('txtErrorMessage');
    if (txtUserName.value == "" || txtPassword.value == "") {
        txtErrorMessage.innerText = "用户名和密码都必须输入！";
        return;
    }
    const account = {
        username: txtUserName.value,
        password: txtPassword.value
    };

    Ext.Ajax.request({
        url: '/login',
        method: 'POST',
        jsonData: account,
        success: function (response, opts) {
            try {
                let authToken = Ext.decode(response.responseText);
                if(!authToken.success){
                    Ext.Msg.alert("系统提示",authToken.message);
                    return;
                }

                let authData = Ext.encode(authToken.data);
                sessionStorage.setItem("authentication_data",authData);

                window.location.href="home";
            } catch (e) {
                handleFailure(response, opts);
            }
        }, failure: function (response, opts) {
            console.log(response.responseText);
            txtErrorMessage.innerText="用户名或密码错误，请重试。"
           // handleFailure(response, opts);
        }
    });
}