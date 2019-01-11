<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:msxsl="urn:schemas-microsoft-com:xslt" exclude-result-prefixes="msxsl"
xmlns="http://www.w3.org/2000/svg">
<xsl:output method="xml" indent="yes"/>

<!--Test Scenario Metrics-->
	<xsl:variable name="scenarios_failed">
		<xsl:value-of select="count(//test-scenario[count(.//report[@status='Failed'])>0])" />
	</xsl:variable>
	<xsl:variable name="scenarios_warning">
		<xsl:value-of select="count(//test-scenario[count(.//report[@status='Failed'])=0 and count(.//report[@status='Warning'])>0])" />
	</xsl:variable>
	<xsl:variable name="scenarios_passed">
		<xsl:value-of select="count(//test-scenario[count(.//report[@status='Failed' or @status='Warning'])=0 and count(.//report[@status='Passed' or @status='Done'])>0 and (count(.//report[@status='Passed' or @status='Done'])=count(.//report)-count(.//report[@status='Info']))])" />
	</xsl:variable>
	<xsl:variable name="scenarios_incomplete">
		<xsl:value-of select="count(//test-scenario[count(.//report[@status='Failed' or @status='Warning'])=0 and count(.//report[@status='Passed' or @status='Done'])>0 and not(count(.//report[@status='Passed' or @status='Done'])=count(.//report)-count(.//report[@status='Info']))])" />
	</xsl:variable>
	<xsl:variable name="scenarios_norun">
		<xsl:value-of select="count(//test-scenario[count(.//report[@status='Passed' or @status='Done' or @status='Failed' or @status='Warning'])=0])" />
	</xsl:variable>
	<xsl:variable name="scenarios_total">
		<xsl:value-of select="count(//test-scenario)" />
	</xsl:variable>

<!--Test Case Metrics-->
	<xsl:variable name="cases_failed">
		<xsl:value-of select="count(//test-case[count(.//report[@status='Failed'])>0])" />
	</xsl:variable>

	<xsl:variable name="cases_warning">
		<xsl:value-of select="count(//test-case[count(.//report[@status='Failed'])=0 and count(.//report[@status='Warning'])>0])" />
	</xsl:variable>

	<xsl:variable name="cases_passed">
		<xsl:value-of select="count(//test-case[count(.//report[@status='Failed' or @status='Warning'])=0 and count(.//report[@status='Passed' or @status='Done'])>0 and (count(.//report[@status='Passed' or @status='Done'])=count(.//report)-count(.//report[@status='Info']))])" />
	</xsl:variable>

	<xsl:variable name="cases_incomplete">
		<xsl:value-of select="count(//test-case[count(.//report[@status='Failed' or @status='Warning'])=0 and count(.//report[@status='Passed' or @status='Done'])>0 and not(count(.//report[@status='Passed' or @status='Done'])=count(.//report)-count(.//report[@status='Info']))])" />
	</xsl:variable>

	<xsl:variable name="cases_norun">
		<xsl:value-of select="count(//test-case[count(.//report[@status='Passed' or @status='Done' or @status='Failed' or @status='Warning'])=0])" />
	</xsl:variable>

	<xsl:variable name="cases_total">
		<xsl:value-of select="count(//test-case)" />
	</xsl:variable>

<!--Test Step Metrics-->
	<xsl:variable name="steps_failed">
	<xsl:value-of select="count(//test-step[count(.//report[@status='Failed'])>0])" />
	</xsl:variable>

	<xsl:variable name="steps_warning">
	<xsl:value-of select="count(//test-step[count(.//report[@status='Failed'])=0 and count(.//report[@status='Warning'])>0])" />
	</xsl:variable>

	<xsl:variable name="steps_passed">
	<xsl:value-of select="count(//test-step[count(.//report[@status='Failed' or @status='Warning'])=0 and count(.//report[@status='Passed' or @status='Done'])>0 and (count(.//report[@status='Passed' or @status='Done'])=count(.//report)-count(.//report[@status='Info']))])" />
	</xsl:variable>

	<xsl:variable name="steps_incomplete">
	<xsl:value-of select="count(//test-step[count(.//report[@status='Failed' or @status='Warning'])=0 and count(.//report[@status='Passed' or @status='Done'])>0 and not(count(.//report[@status='Passed' or @status='Done'])=count(.//report)-count(.//report[@status='Info']))])" />
	</xsl:variable>

	<xsl:variable name="steps_norun">
	<xsl:value-of select="count(//test-step[count(.//report[@status='Passed' or @status='Done' or @status='Failed' or @status='Warning'])=0])" />
	</xsl:variable>

	<xsl:variable name="steps_total">
	<xsl:value-of select="count(//test-step)" />
	</xsl:variable>

<xsl:template match="root">
	<html>
	<head>	
	<title>Progression Report Manager</title>
	<script src="http://code.jquery.com/jquery-1.11.0.min.js">//</script>
	<script>
	$( document ).ready(function() {

		function expandContent(element) {
			element.parent().nextAll(".box, .line, line2").show();
			element.attr("class","collapse");
			element.unbind("click");
			element.text("[-]");
			element.click(function( event ) {
				collapseContent($(this));
			});
		};
		
		function collapseContent(element) {
			element.parent().nextAll(".box, .line, line2").hide();
			element.attr("class","expand");
			element.unbind("click");
			element.text("[+]");
			element.click(function( event ) {
				expandContent($(this));
			});
		};
		
		$( "span.expand" ).click(function( event ) {
			expandContent($(this));
		});
		
		$( "span.collapse" ).click(function( event ) {
			collapseContent($(this));
		});
		
		
		$( "span.collapse-all" ).click(function( event ) {
			$( "span.collapse" ).click();
		});
		
		$( "span.expand-all" ).click(function( event ) {
			$( "span.expand" ).click();
		});
		
		$( "div.box[id='test-case']" ).find("span.collapse:first").click();
		$( "div.box[id='test-step']" ).find("span.collapse:first").click();
		$( "div.box[id='test-webservice']" ).find("span.collapse:first").click();
		
	});
    </script>
	<style>

	div.centerit{
        width: 90%;
        margin: 0 auto; 
    }

    table.metrics { 
        margin: 0 auto; 
        float: center;
        font-family:"Calibri";
        font-size: 9pt;
        border: solid gray 1px;
    }

    table.metrics td{
            text-align:center;
            border: solid #aaa 1px;
    }

    div.mainBox{
            text-align:center;
            border: solid #aaa 1px;
            -webkit-border-radius: 10px; -moz-border-radius: 10px; border-radius: 10px;
            padding:10px 0;
    }

    div.line {
            font-family:"Calibri";
            font-size: 9pt;
            display: block;
            width: inherit;
            border-top: solid #aaa 1px;
            width:inherit;
            margin:0;
            padding:3px 0;
        }

    div.line span {
    	padding:0 10px;
    }
    
    div.line2 {
            font-family:"Calibri";
            font-size: 8pt;
            display: block;
            width: inherit;
            border-top: solid #aaa 1px;
            width:inherit;
            margin:0;
            padding:3px 0;
        }

    div.line2 span {
    	padding:0 10px;
    }


    h1 {
        font-size: 16pt;
        text-left;
        width: 700px;
    }       
    div.box {
        font-family:"Calibri";
        font-size: 9pt;
        display: block;     
        width: inherit;
        border: none;
        margin: 0 auto; 
        padding-bottom: 0px;
        text-align:left;
    }

    div.header_box{
    	float:left; width:100%; background:#aaa; color:#fff !important; -webkit-border-top-left-radius: 10px; -webkit-border-top-right-radius: 10px; -moz-border-radius-topleft: 10px; -moz-border-radius-topright: 10px; border-top-left-radius: 10px; border-top-right-radius: 10px;
    	font-family:"Calibri";
        font-size: 9pt;        
        text-align:left;        
        color: black;
    }

    div.header_box div {
    	padding:6px 2px;
    }

    div.header {
        float: left;
        font-family:"Calibri";
        font-size: 9pt;        
        text-align:left;
        color: white;
        min-height: 19px;
        width: 360px;
    }

    div.header div {
    	padding:10px !important;
    }

    div.environment {
        float: left;
        width: 120px;
    }

    div.time {
        float: left;
        width: 180px;
    }

    div.header1 {
    	font-family:"Calibri";
        font-size: 9pt;        
        text-align:left;        
        color: black;
        background-color: #9b9da4;        
    }

    div.header2 {
    	font-family:"Calibri";
        font-size: 9pt;        
        text-align:left;        
        color: black;
        background-color: #ffffff;
        width: inherit;
        border-top: solid #bfbfbf 1px;
        width:100%;
        margin:0 auto;
        min-height: 19px;        
    }
    
    div.header3 {
    	font-family:"Calibri";
        font-size: 9pt;        
        text-align:left;        
        color: black;
        background-color: #ffffff;
        width: inherit;
        border-top: solid #bfbfbf 1px;
        width:100%;
        margin:0 auto;
        min-height: 19px;        
    }
    span.passed {
        color: green;
    }
    span.failed {
        color: red;
    }
    span.warning {
        color: orange;
    }
    span.norun, span.incomplete {
        color: grey;
    }
    span.info {
        color: #3377ff;
    }
	
	span.webservice {
        color: green;
    }
    span.done {
        color: green;
    }
    span.status {
        color: grey;
    }
    span.expand, span.collapse {
        font-family:"Courier";
        font-size: 10pt;
        color: black;
        font-weight: bold;
    }
    
   span.expand2, span.collapse2 {
        font-family:"Courier";
        font-size: 8pt;
        color: black;
        font-weight: bold;
    }
    
    a:link {
        color: #3377ff;
    }

    .title_table {
	    font-family: Calibri;
	    font-size: 8pt;
	    font-weight: bold;
	    background: black;
	    color: antiquewhite;
    }

    .title_table tr td {
    	padding:5px 10px;
    }
        
    
    </style>

	</head>
		<body>
		 <div class="centerIt">              	      	
			<xsl:apply-templates select="test-scenario"/>
		</div>
		</body>
		
	</html>
</xsl:template>

	<!--Template for Test Scenarios--> 
	<xsl:template match="test-scenario">
		 <div class="header_box">
	            	<table width="100%" class="title_table">
		            	<tr>
		            		<td width="30%">
			            		<xsl:value-of select="@name"/><span class="status"> | <xsl:choose>
			            		 <xsl:when test=".//report[@status='Failed'] or .//test-step/test-webservice/reportwebservice[@status='Failed']">
									<span class="failed">Failed</span>
								  </xsl:when>
								  <xsl:when test=".//report[@status='Warning'] or .//test-step/test-webservice/reportwebservice[@status='Warning']">
									<span class="warning">Warning</span>
								  </xsl:when>
								  <xsl:otherwise>
								  	 <xsl:variable name="reportStep">
								  	    <xsl:choose>
								            <xsl:when test=".//report[@status='Passed']">
								                <xsl:value-of select="true()" />
								            </xsl:when>
								            <xsl:when test="not(.//report)">
								                <xsl:value-of select="true()" />
								            </xsl:when>
								            <xsl:otherwise>
								                <xsl:value-of select="false()" />
								            </xsl:otherwise>
						        		</xsl:choose>
						    		</xsl:variable>
						    		 <xsl:variable name="reportWebService">
								  	    <xsl:choose>
								  	    	<xsl:when test=".//test-step/test-webservice/reportwebservice[@status='Passed']">
								                <xsl:value-of select="true()" />
								            </xsl:when>
								            <xsl:when test="not(.//test-step/test-webservice/reportwebservice)">
								                <xsl:value-of select="true()" />
								            </xsl:when>
								            
								            <xsl:otherwise>
								                <xsl:value-of select="false()" />
								            </xsl:otherwise>
						        		</xsl:choose>
						    		</xsl:variable>
						    		
						    		<xsl:choose>
										     <xsl:when test="$reportStep and $reportWebService">
										     	<span class="passed">Passed</span>
										     </xsl:when>
										     <xsl:otherwise>
										     	<span class="norun">No Run</span>
										     </xsl:otherwise>
									   	</xsl:choose>
								  </xsl:otherwise>
								</xsl:choose></span>
		            		</td>
							<td width="15%">
								<div><span>Environment : </span><xsl:value-of select="@environment"/></div>										
						    </td>					
						    <td width="15%">										
						    	<div><span>Browser : </span><xsl:value-of select="@browser"/></div>
						    </td>
						    <td width="20%">
								<div><span>Time Started : </span><xsl:value-of select="@startTime"/></div>										
						    </td>
						    <td width="20%">
								<div><span>End Started: </span><xsl:value-of select="@endTime"/></div>										
						    </td>
		            	</tr>
	            	</table>
		</div>
		<div class="mainBox">
			<xsl:apply-templates select="summary"/>	
			<xsl:apply-templates select="test-case"/>
		</div>	
	</xsl:template>


<!--Template for Summary/Details--> 
<xsl:template match="summary|details">
	<xsl:for-each select="href">		
	  <div class="line"><span class="label">Video Playback</span> : <a>
	  <xsl:attribute name="href"><xsl:value-of select="@desc"/></xsl:attribute>
	  <xsl:attribute name="target">_blank</xsl:attribute><xsl:value-of select="@name"/>
	</a></div>
	</xsl:for-each>
	<xsl:for-each select="info">
		<div class="line"><span class="label"><xsl:value-of select="@name" /></span> : <xsl:value-of select="@desc" /></div>
	</xsl:for-each>
</xsl:template>

<!--Template for Test Cases--> 
<xsl:template match="test-case">
	<div class="box" id="test-case">
		<div class="header1"><span class="collapse" href="#">[-]</span>Test Case: <xsl:value-of select="@name"/><span class="status"> | <xsl:choose>
		    <xsl:when test=".//report[@status='Failed'] or .//test-step/test-webservice/reportwebservice[@status='Failed']">
			<span class="failed">Failed</span>
		  </xsl:when>
		  <xsl:when test=".//report[@status='Warning'] or .//test-step/test-webservice/reportwebservice[@status='Warning']">
			<span class="warning">Warning</span>
		  </xsl:when>
		  <xsl:otherwise>
		  	 <xsl:variable name="reportStep">
		  	    <xsl:choose>
		            <xsl:when test=".//report[@status='Passed']">
		                <xsl:value-of select="true()" />
		            </xsl:when>
		            <xsl:when test="not(.//report)">
		                <xsl:value-of select="true()" />
		            </xsl:when>
		            <xsl:otherwise>
		                <xsl:value-of select="false()" />
		            </xsl:otherwise>
        		</xsl:choose>
    		</xsl:variable>
    		 <xsl:variable name="reportWebService">
		  	    <xsl:choose>
		  	    	<xsl:when test=".//test-step/test-webservice/reportwebservice[@status='Passed']">
		                <xsl:value-of select="true()" />
		            </xsl:when>
		            <xsl:when test="not(.//test-step/test-webservice/reportwebservice)">
		                <xsl:value-of select="true()" />
		            </xsl:when>
		            
		            <xsl:otherwise>
		                <xsl:value-of select="false()" />
		            </xsl:otherwise>
        		</xsl:choose>
    		</xsl:variable>
    		
    		<xsl:choose>
				     <xsl:when test="$reportStep and $reportWebService">
				     	<span class="passed">Passed</span>
				     </xsl:when>
				     <xsl:otherwise>
				     	<span class="norun">No Run</span>
				     </xsl:otherwise>
			   	</xsl:choose>
		  </xsl:otherwise>
		</xsl:choose></span>
		</div>
		<xsl:apply-templates select="summary"/>
		<xsl:apply-templates select="test-step"/>
		<xsl:apply-templates select="report|attachment|screenshot"/>
	</div>
</xsl:template>

<!--Template for Test Steps--> 
<xsl:template match="test-step">
	<div class="box" id="test-step">
		<div class="header2"><span style="margin-left:7px" class="collapse" href="#">[-]</span> Step: <xsl:value-of select="@name"/><span class="status"> | <xsl:choose>		
		  <xsl:when test=".//report[@status='Failed'] or .//reportwebservice[@status='Failed']">
			<span class="failed">Failed</span>
		  </xsl:when>
		  <xsl:when test=".//report[@status='Warning'] or .//reportwebservice[@status='Warning']">
			<span class="warning">Warning</span>
		  </xsl:when>
		  <xsl:otherwise>
		  	 <xsl:variable name="reportStep">
		  	    <xsl:choose>
		            <xsl:when test=".//report[@status='Passed']">
		                <xsl:value-of select="true()" />
		            </xsl:when>
		            <xsl:when test="not(.//report)">
		                <xsl:value-of select="true()" />
		            </xsl:when>
		            <xsl:otherwise>
		                <xsl:value-of select="false()" />
		            </xsl:otherwise>
        		</xsl:choose>
    		</xsl:variable>
    		<xsl:variable name="reportWebService">
		  	    <xsl:choose>
		  	    	<xsl:when test=".//reportwebservice[@status='Passed']">
		                <xsl:value-of select="true()" />
		            </xsl:when>
		            <xsl:when test="not(.//reportwebservice)">
		                <xsl:value-of select="true()" />
		            </xsl:when>
		            
		            <xsl:otherwise>
		                <xsl:value-of select="false()" />
		            </xsl:otherwise>
        		</xsl:choose>
    		</xsl:variable>
    		
    		<xsl:choose>
				     <xsl:when test="$reportStep and $reportWebService">
				     	<span class="passed">Passed</span>
				     </xsl:when>
				     <xsl:otherwise>
				     	<span class="norun">No Run</span>
				     </xsl:otherwise>
			   	</xsl:choose>
		  </xsl:otherwise>
		</xsl:choose></span>
		</div>
		<xsl:apply-templates select="test-webservice|report|attachment|screenshot"/>
	</div>
</xsl:template>

<!--Template for Web Service--> 
<xsl:template match="test-webservice">
	<div class="box" id="test-webservice">
		<div class="header3"><span style="margin-left:14px" class="collapse" href="#">[-]</span> Web Service: <xsl:value-of select="@name"/><span class="status"> | <xsl:choose>
		  <xsl:when test=".//reportwebservice[@status='Failed']">
			<span class="failed">Failed</span>
		  </xsl:when>
		  <xsl:when test=".//reportwebservice[@status='Warning']">
			<span class="warning">Warning</span>
		  </xsl:when>
		  <xsl:when test=".//reportwebservice[@status='Passed']">
		   	<span class="passed">Passed</span>
		  </xsl:when>
		  <xsl:otherwise>
			<span class="norun">No Run</span>
		  </xsl:otherwise>
		</xsl:choose></span>
		</div>
		<xsl:apply-templates select="reportwebservice"/>
	</div>
</xsl:template>

<!--Template for Test Reports--> 
<xsl:template match="reportwebservice">
	<div class="line">
		<span width="100px" style="margin-left:14px">  <xsl:value-of select="@timestamp"/></span> | <b><span>
		<xsl:choose>
		  <xsl:when test="@status='Passed'">
			<xsl:attribute name="class">passed</xsl:attribute>
		  </xsl:when>
		  <xsl:when test="@status='Failed'">
			<xsl:attribute name="class">failed</xsl:attribute>
		  </xsl:when>
		  <xsl:when test="@status='Warning'">
			<xsl:attribute name="class">warning</xsl:attribute>
		  </xsl:when>
		  <xsl:when test="@status='Done'">
			<xsl:attribute name="class">done</xsl:attribute>
		  </xsl:when>
		  <xsl:when test="@status='Info'">
			<xsl:attribute name="class">info</xsl:attribute>
		  </xsl:when>
		  <xsl:when test="@status='webservice'">
			<xsl:attribute name="class">webservice</xsl:attribute>
		  </xsl:when>
		  <xsl:when test="@status='xml'">				
		  	<xsl:attribute name="class">xml</xsl:attribute>					
		  </xsl:when>
		</xsl:choose>
		<xsl:value-of select="@status"/>
		</span></b> | 
		
		
		<xsl:choose>		  
		  <xsl:when test="@status='xml'">
			<a> <xsl:attribute name="href"><xsl:value-of select="@desc"/></xsl:attribute><xsl:value-of select="@name"/></a>
		  </xsl:when>
		  <xsl:otherwise>				
			<xsl:value-of select="@desc"/>
		  </xsl:otherwise>
		</xsl:choose>
		
	</div>
</xsl:template>


<!--Template for Test Reports--> 
<xsl:template match="report">
	<div class="line">
		<span width="100px">  <xsl:value-of select="@timestamp"/></span> | <b><span>
		<xsl:choose>
		  <xsl:when test="@status='Passed'">
			<xsl:attribute name="class">passed</xsl:attribute>
		  </xsl:when>
		  <xsl:when test="@status='Failed'">
			<xsl:attribute name="class">failed</xsl:attribute>
		  </xsl:when>
		  <xsl:when test="@status='Warning'">
			<xsl:attribute name="class">warning</xsl:attribute>
		  </xsl:when>
		  <xsl:when test="@status='Done'">
			<xsl:attribute name="class">done</xsl:attribute>
		  </xsl:when>
		  <xsl:when test="@status='Info'">
			<xsl:attribute name="class">info</xsl:attribute>
		  </xsl:when>
		  <xsl:when test="@status='webservice'">
			<xsl:attribute name="class">webservice</xsl:attribute>
		  </xsl:when>
		  <xsl:when test="@status='xml'">				
		  	<xsl:attribute name="class">xml</xsl:attribute>					
		  </xsl:when>
		</xsl:choose>
		<xsl:value-of select="@status"/>
		</span></b> | 
		
		
		<xsl:choose>		  
		  <xsl:when test="@status='xml'">
			<a> <xsl:attribute name="href"><xsl:value-of select="@desc"/></xsl:attribute><xsl:value-of select="@name"/></a>
		  </xsl:when>
		  <xsl:otherwise>				
			<xsl:value-of select="@desc"/>
		  </xsl:otherwise>
		</xsl:choose>
		
	</div>
</xsl:template>

<!--Template for Test Attachements--> 
<xsl:template match="attachment">
	<div class="line"><span class="label">PDF File</span> : <a>
		<xsl:attribute name="href">
			<xsl:value-of select="@file"/>
	  	</xsl:attribute>
	  	<xsl:attribute name="download">pdf</xsl:attribute><xsl:value-of select="@name"/>
	</a>	  
	</div>
</xsl:template>

<!--Template for Test Attachements--> 
<xsl:template match="pdf">
	<div class="line"><span class="label">Attachment</span> : <a>
	  <xsl:attribute name="href">
		<xsl:value-of select="@file"/>
	  </xsl:attribute>
	  <xsl:attribute name="target">_blank</xsl:attribute><xsl:value-of select="@name"/>
	</a>
	</div>
</xsl:template>

<!--Template for Test Screenshots--> 
<xsl:template match="screenshot">
	<div class="line"><span class="label">Screenshot</span> : <a>
	  <xsl:attribute name="href">data:image/png;base64,<xsl:value-of select="@base64"/></xsl:attribute>
	  <xsl:attribute name="target">_blank</xsl:attribute><xsl:value-of select="@name"/>
	</a></div>
</xsl:template>

</xsl:stylesheet>