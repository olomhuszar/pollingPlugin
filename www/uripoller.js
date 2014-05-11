var uripoller =  {
    startEvent: function( serverAddress, token, successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'URIPoller',
            'startPolling',
            [{
                "token": token,
                "serverAddress": serverAddress
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