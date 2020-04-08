<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <xsl:element name="ul">
            <xsl:attribute name="id">member_tree</xsl:attribute>
            <xsl:attribute name="class">easyui-tree</xsl:attribute>
            <xsl:apply-templates select="m" />
        </xsl:element>
    </xsl:template>
    <xsl:template match="m">
        <xsl:element name="li">
            <xsl:attribute name="id">
                <xsl:value-of select="@id" />
            </xsl:attribute>
            <xsl:element name="span">
                <xsl:value-of select="@n"/>
                <xsl:element name="span">
                    <xsl:attribute name="id">mem_<xsl:value-of select="@id" /></xsl:attribute>
                </xsl:element>

            </xsl:element>
            <xsl:if test="count(m)>0">
                <xsl:element name="ul">
                    <xsl:for-each select="m">
                        <xsl:apply-templates select="." />
                    </xsl:for-each>
                </xsl:element>
            </xsl:if>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>