<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" indent="yes"/>

    <xsl:key name="plansByOperator" match="Plan" use="OperatorName"/>

    <!-- Root element -->
    <xsl:template match="/Tariff">
        <Tariff>
            <!-- Generate groups for each unique OperatorName -->
            <xsl:for-each select="Plan[generate-id() = generate-id(key('plansByOperator', OperatorName)[1])]">
                <xsl:element name="{OperatorName}">
                    <!-- Group plans with the same OperatorName -->
                    <xsl:for-each select="key('plansByOperator', OperatorName)">
                        <xsl:copy-of select="."/>
                    </xsl:for-each>
                </xsl:element>
            </xsl:for-each>
        </Tariff>
    </xsl:template>
</xsl:stylesheet>
