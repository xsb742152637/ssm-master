<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <xsl:element name="ul">
            <xsl:attribute name="id">menu_tree</xsl:attribute>
            <xsl:attribute name="class">easyui-tree</xsl:attribute>
            <xsl:apply-templates select="a" />
        </xsl:element>
    </xsl:template>
    <xsl:template match="a">
        <xsl:element name="li">
            <xsl:attribute name="id">
                <xsl:value-of select="@id" />
            </xsl:attribute>
            <!--xsl:if test="count(@project)>0">
                <xsl:attribute name="project">
                    <xsl:value-of select="@project" />
                </xsl:attribute>
            </xsl:if-->
            <xsl:element name="span">
                <xsl:value-of select="@n"/>
                <!--xsl:if test="count(org)>0">
                    (
                    <xsl:for-each select="org">
                        <xsl:value-of select="@id"/>,
                    </xsl:for-each>
                    )
                </xsl:if-->
                <xsl:element name="span">
                    <xsl:attribute name="id"><xsl:value-of select="@id" /></xsl:attribute>
                </xsl:element>
                <!--<xsl:element name="span">-->
                    <!--<xsl:attribute name="id">img_r_<xsl:value-of select="@id" /></xsl:attribute>-->
                    <!--<xsl:attribute name="class"></xsl:attribute>-->
                    <!--<xsl:attribute name="onclick">checkbox_action(this,'read')</xsl:attribute>-->
                    <!--<xsl:attribute name="title"><xsl:value-of select="@n" />的查看权限</xsl:attribute>-->
                <!--</xsl:element>-->
                <!--<xsl:element name="span">-->
                    <!--<xsl:attribute name="id">img_u_<xsl:value-of select="@id" /></xsl:attribute>-->
                    <!--<xsl:attribute name="class"></xsl:attribute>-->
                    <!--<xsl:attribute name="onclick">checkbox_action(this,'update')</xsl:attribute>-->
                    <!--<xsl:attribute name="title"><xsl:value-of select="@n" />的编辑权限</xsl:attribute>-->
                <!--</xsl:element>-->
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