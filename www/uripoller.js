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
    },
    stopEvent: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'URIPoller',
            'stopPolling',
            []
        );
    }
}
module.exports = uripoller;