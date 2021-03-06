<%--
  Created by IntelliJ IDEA.
  Author: Joseph Lee <josel@pdx.edu>
  Date: 7/17/13
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>GenBank-Query</title>
    <meta name="layout" content="main"/>

    <gvisualization:apiImport />

</head>
<body>

<g:if test="${codonDistributions.size() == 0}">
    <script>
        // show modal if no data supplied yet
        $(document).ready(function() {
            $('#upload').modal({ show: true });
        });
    </script>
</g:if>
<g:elseif test="${codonDistributions.size() == 1}">
    <%-- Single Sequence Analysis --%>
    <%-- Codon Distribution --%>
    <div id="codon-dist">
        <%
            // Set options
            def codonDist = codonDistributions[0]
            def columnHeaders = [['string', 'Codon'], ['number', 'Distribution']]
            def textStyle = [fontSize: 10]
            def titleTextStyle = [fontSize: 13]
            def options = [
                    vAxis: [maxValue: 1, minValue: 0, textStyle: textStyle],
                    hAxis: [textStyle: textStyle],
                    legend: [position: 'none'],
                    chartArea: [top: 40, bottom: 0, left: 40, right: 0]
            ]
            def rowCounts = [2, 3, 2, 2, 5, 4, 3, 2]    // Graph row lengths
            def c = 0
            def i = 0
            def codonList = codonDist.collectNested { it.name }
            def amino = null

        %>
        <g:each in="${rowCounts}" var="r">
            <div class="row span12">
                <% c = 0 %>
                <g:while test="${c < r}">
                    <%
                        amino = codonDist[i]
                    %>
                    <div class="dist-graph" id="${"amino" + i.toString()}"></div>
                    <gvisualization:columnCoreChart columns="${columnHeaders}" data="${amino.values}"
                        elementId="${"amino" + i.toString()}" title="${amino.name}" vAxis="${new Expando(options.vAxis)}"
                        legend="${'none'}" height="${200}" width="${40 + amino.values.size() * 80}"
                        titleTextStyle="${new Expando(titleTextStyle)}"
                        chartArea="${new Expando(options.chartArea)}" />
                    <%
                        c = c + 1
                        i = i + 1
                    %>
                </g:while>
            </div>
        </g:each>

    </div>
</g:elseif>


</body>
</html>