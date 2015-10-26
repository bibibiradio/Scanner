var net = require("net");
var qstr = require("querystring");
net.createServer(function (conn) {
    conn.on('data', function (data) {
   		var textData = data.toString();
   		var tab = textData.split(" ");
   		var param = "helloworld"
   		if(tab.length<=1){
   			return;
   		}
   		var urlPath = tab[1];
   		console.log(urlPath);
   		if(urlPath.indexOf("param=")!=-1){
   			var idx = urlPath.indexOf("param=")+"param=".length;
   			param = qstr.unescape(urlPath.substring(idx));
   			console.log(param)
   		}
        conn.write([
            'HTTP/1.1 200 OK',
            'Content-Type: text/plain',
            'Content-Length: '+data.toString().length,
            "goodhead: "+param,
            '',
            data.toString()
        ].join('\r\n'));
    });
}).listen(80);