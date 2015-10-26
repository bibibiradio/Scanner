//var http=require("http");
var express=require("express");
//var bodyParser = require('body-parser');
var templateViewer=require("./TemplateViewer").getTemplateViewer();
var app=express();
var path=require("path");
var sys=require("sys")

console.log(__dirname);

//app.use(bodyParser());
//app.use(express.methodOverride());
//app.use(app.router);

app.listen(80,function(){});

app.get("/xss_in_content",function(req,res){
	var resData=templateViewer.render("./xss_in_content_template.ejs",{"xss_param":req.param("param")});
	return res.end(resData);
});

app.get("/xss_in_href",function(req,res){
	var resData=templateViewer.render("./xss_in_href_template.ejs",{"xss_param":req.param("param")});
	return res.end(resData);
});

app.get("/xss_in_script",function(req,res){
	var resData=templateViewer.render("./xss_in_script_template.ejs",{"xss_param":req.param("param")});
	return res.end(resData);
});

app.get("/xss_in_on",function(req,res){
	var resData=templateViewer.render("./xss_in_on_template.ejs",{"xss_param":req.param("param")});
	return res.end(resData);
});

app.get("/xss_in_on_have_escape",function(req,res){
	var resData=templateViewer.render("./xss_in_on_have_escape_template.ejs",{"xss_param":req.param("param")});
	return res.end(resData);
});

app.get("/xss_in_href_have_escape",function(req,res){
	var resData=templateViewer.render("./xss_in_href_have_escape_template.ejs",{"xss_param":req.param("param")});
	return res.end(resData);
});

app.get("/header_inject_no",function(req,res){
	//var resData=templateViewer.render("./xss_in_href_have_escape_template.ejs",{"xss_param":req.param("param")});
	res.end(req.param("param"));
});

app.get("/header_inject_yes",function(req,res){
	//var resData=templateViewer.render("./xss_in_href_have_escape_template.ejs",{"xss_param":req.param("param")});
	// res.set({
	// 	"testHead":req.param("param")
	// })
	var param = req.param("param");
	if(param != null){
		var headers = param.split("\r\n");
		if(headers.length <= 1){
			res.setHeader("testHead",req.param("param"));
			res.writeHeader(200,{"testHead":req.param("param")});
		}else{
			var header = headers[1];
			var keyValue = header.split(":");

			if(keyValue.length <= 1){
				res.setHeader("testHead",req.param("param"));
				res.writeHeader(200,{"testHead":req.param("param")});
			}else{
				res.setHeader("testHead",headers[0]);
				res.setHeader(keyValue[0],keyValue[1]);
			}
		}
	}
	//console.log(res);
	//res.headers["testHead"]=req.param("param")
	res.end(req.param("param"));
});

app.get("/id_card_sensitive",function(req,res){
	var resData=templateViewer.render("./sensitive_test_data.html");
	return res.end(resData);
});