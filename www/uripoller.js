var uripoller =  {
    startEvent: function(token, state, successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'URIPoller',
            'startPolling',
            [{
                "token": token,
                "state": state
            }]
        );
    }
}
module.exports = uripoller;