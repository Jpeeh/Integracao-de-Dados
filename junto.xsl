<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>
    <xsl:strip-space elements="*"/>
    
    <xsl:variable name="Obras" select="document('obras.xml')"/>
    
    <xsl:template match="Autor">
        <xsl:copy>
            <xsl:apply-templates select="Autor"/>
			<xsl:value-of select="@id"/>
        </xsl:copy>
    </xsl:template>
    
    <xsl:template match="Autor">
        <xsl:copy>
            <xsl:apply-templates select="@*|Nome|$Obras/*/Livro[Autor=current()/Nome]/Titulo"/>
        </xsl:copy>        
    </xsl:template>
    
    <xsl:template match="Titulo">
        <livro>
            <titulo><xsl:value-of select="."/></titulo>
        </livro>	
    </xsl:template>
</xsl:stylesheet>