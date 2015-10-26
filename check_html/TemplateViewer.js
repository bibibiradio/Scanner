var fs=require("fs");
var ejs=require("ejs");

var templateViewer={
	"templateGetFileString":getTemplateFileStr,
	"render":render,
	"templateFileRoot":"",
	"templateCacheData":{}
};

function getTemplateViewer(templateFileRootPath){
	if(templateFileRootPath!=null){
		templateViewer.templateFileRoot=templateFileRootPath;
	}
	return templateViewer;
}

function getTemplateFileStr(templateFilePath){
	var absTemplateFilePath="";
	if(templateViewer.templateFileRoot.length!=0){
		absTemplateFilePath=templateViewer.templateFileRoot+"/"+templateFilePath;
	}else{
		absTemplateFilePath=templateFilePath;
	}

	if(templateViewer.templateCacheData[absTemplateFilePath]!=null){
		return templateViewer.templateCacheData[absTemplateFilePath];
	}

	var templateString=fs.readFileSync(absTemplateFilePath,"utf-8");

	templateViewer.templateCacheData[absTemplateFilePath]=templateString;

	return templateString
}

function render(templateFilePath,options){
	var templateString=templateViewer.templateGetFileString(templateFilePath)

	if(templateString==null){
		return null;
	}

	var retString=ejs.render(templateString,options);

	return retString;
}

exports.getTemplateViewer=getTemplateViewer;