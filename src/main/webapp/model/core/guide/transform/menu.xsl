<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <xsl:element name="div">
            <xsl:attribute name="id">menu_tree</xsl:attribute>
            <xsl:attribute name="class">layui-tree-set layui-tree-setLineShort</xsl:attribute>
            <xsl:attribute name="data-id"><xsl:value-of select="@id" /></xsl:attribute>
            <xsl:apply-templates select="a" />
        </xsl:element>
    </xsl:template>
    <xsl:template match="a">
        <xsl:element name="div">
            <xsl:attribute name="class">layui-tree-entry</xsl:attribute>
            <xsl:attribute name="id">
                <xsl:value-of select="@id" />
            </xsl:attribute>
            <xsl:element name="div">
                <xsl:attribute name="class">layui-tree-main</xsl:attribute>
                <xsl:if test="count(a)>0">
                    <xsl:element name="span">
                        <xsl:attribute name="class">layui-tree-iconClick layui-tree-icon</xsl:attribute>
                        <xsl:element name="i">
                            <xsl:attribute name="class">layui-icon layui-icon-addition</xsl:attribute>
                        </xsl:element>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="count(a)=0">
                    <xsl:element name="span">
                        <xsl:attribute name="class">layui-tree-iconClick</xsl:attribute>
                    </xsl:element>
                </xsl:if>

                <xsl:element name="span">
                    <xsl:attribute name="class">layui-tree-iconClick2</xsl:attribute>
                    <xsl:element name="i">
                        <xsl:if test="count(a)>0">
                            <xsl:attribute name="class">layui-icon layui-icon-template-1</xsl:attribute>
                        </xsl:if>
                        <xsl:if test="count(a)=0">
                            <xsl:attribute name="class">layui-icon layui-icon-app</xsl:attribute>
                        </xsl:if>
                    </xsl:element>
                </xsl:element>
                <xsl:element name="span">
                    <xsl:attribute name="class">layui-tree-txt layui-tree-color2</xsl:attribute>
                    <xsl:element name="div">
                        <xsl:attribute name="id"><xsl:value-of select="@id" /></xsl:attribute>
                        <xsl:value-of select="@n"/>
                    </xsl:element>
                    <xsl:element name="div">
                        <xsl:attribute name="class">menu_mems</xsl:attribute>

                        <xsl:element name="span">
                            <xsl:attribute name="class">menu_mems_s</xsl:attribute>
                            <xsl:attribute name="style">color: #7676f9</xsl:attribute>
                            <xsl:for-each select="s">
                                <xsl:element name="span"><xsl:attribute name="data-id"><xsl:value-of select="@id" /></xsl:attribute><xsl:value-of select="@n"/>,</xsl:element>
                            </xsl:for-each>
                        </xsl:element>
                        <xsl:element name="span">
                            <xsl:attribute name="class">menu_mems_r</xsl:attribute>
                            <xsl:attribute name="style">color: blue</xsl:attribute>
                            <xsl:for-each select="r">
                                <xsl:element name="span"><xsl:attribute name="data-id"><xsl:value-of select="@id" /></xsl:attribute><xsl:value-of select="@n"/>,</xsl:element>
                            </xsl:for-each>
                        </xsl:element>
                        <xsl:element name="span">
                            <xsl:attribute name="class">menu_mems_u</xsl:attribute>
                            <xsl:attribute name="style">color: #eea236</xsl:attribute>
                            <xsl:for-each select="u">
                                <xsl:element name="span"><xsl:attribute name="data-id"><xsl:value-of select="@id" /></xsl:attribute><xsl:value-of select="@n"/>,</xsl:element>
                            </xsl:for-each>
                        </xsl:element>
                    </xsl:element>
                    <xsl:element name="div">
                        <xsl:attribute name="class">menu_mems_count</xsl:attribute>
                        <xsl:attribute name="style">color:gray</xsl:attribute>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
            <xsl:element name="div">
                <xsl:attribute name="class">layui-tree-other</xsl:attribute>
            </xsl:element>
        </xsl:element>
        <xsl:if test="count(a)>0">
            <xsl:element name="div">
                <xsl:attribute name="class">layui-tree-pack layui-tree-lineExtend</xsl:attribute>
                <xsl:element name="div">
                    <xsl:attribute name="class">layui-tree-set layui-tree-setLineShort</xsl:attribute>
                    <xsl:attribute name="data-id"><xsl:value-of select="@id" /></xsl:attribute>
                    <xsl:for-each select="a">
                        <xsl:apply-templates select="." />
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>

        </xsl:if>
    </xsl:template>
</xsl:stylesheet>