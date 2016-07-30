<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="testResult">
        <testResult>
            <xsl:copy-of select="duration"/>
            <xsl:copy-of select="empty"/>
            <xsl:copy-of select="failCount"/>
            <xsl:copy-of select="passCount"/>
            <xsl:copy-of select="skipCount"/>
            <xsl:for-each select="suite">
                <suite>
                    <xsl:for-each select="case">
                        <xsl:copy-of select="age"/>
                        <xsl:copy-of select="className"/>

                        <xsl:copy-of select="duration"/>
                        <xsl:copy-of select="failedSince"/>
                        <xsl:copy-of select="name"/>
                        <xsl:copy-of select="skipped"/>
                        <xsl:copy-of select="status"/>
                    </xsl:for-each>
                    <xsl:copy-of select="duration"/>
                    <xsl:copy-of select="name"/>
                    <!-- stdout could be huge so filter it out -->
                    <!--<xsl:copy-of select="stderr"/>-->
                    <!--<xsl:copy-of select="stdout"/>-->

                    <xsl:copy-of select="timestamp"/>
                </suite>
            </xsl:for-each>
        </testResult>
    </xsl:template>
</xsl:stylesheet>

