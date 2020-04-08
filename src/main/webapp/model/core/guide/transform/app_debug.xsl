<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <xsl:element name="ul">
            <xsl:attribute name="id">menu_debug_tree</xsl:attribute>
            <xsl:attribute name="class">easyui-tree</xsl:attribute>
            <xsl:apply-templates select="a" />
        </xsl:element>
    </xsl:template>
    <xsl:template match="a">
        <xsl:element name="li">
            <xsl:attribute name="id">
                <xsl:value-of select="@id" />
            </xsl:attribute>
            <xsl:element name="span">
                <xsl:value-of select="@n"/>

                <xsl:element name="span">
                    <xsl:attribute name="id">debug_<xsl:value-of select="@id" /></xsl:attribute>
                </xsl:element>
                <xsl:element name="span">
                    <xsl:attribute name="style">color:gray</xsl:attribute>
                    <xsl:for-each select="s">
                        <xsl:value-of select="@n"/>,
                    </xsl:for-each>
                </xsl:element>
                <xsl:element name="span">
                    <xsl:attribute name="style">color:blue</xsl:attribute>
                    <xsl:for-each select="r">
                        <xsl:value-of select="@n"/>,
                    </xsl:for-each>
                </xsl:element>
                <xsl:element name="span">
                    <xsl:attribute name="style">color:red</xsl:attribute>
                    <xsl:for-each select="u">
                        <xsl:value-of select="@n"/>,
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>
            <xsl:if test="count(a)>0">
                <xsl:element name="ul">
                    <xsl:for-each select="a">
                        <xsl:apply-templates select="." />
                    </xsl:for-each>
                </xsl:element>
            </xsl:if>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>