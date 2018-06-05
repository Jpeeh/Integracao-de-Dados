<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:output method="html"></xsl:output>
    <xsl:template match="autores">
        <html>
            <body>
                <h1>Fotografias dos Autores</h1>
                <xsl:apply-templates select="Autor"></xsl:apply-templates>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="Autor">
        <xsl:for-each select=".">
            <h3><xsl:value-of select="./Link_Foto"/></h3>
            <img>
                <xsl:attribute name="src">
                    <xsl:value-of select="./Link_Foto"/>
                </xsl:attribute>
            </img>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>