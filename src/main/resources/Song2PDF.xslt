<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
				xmlns="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="/">
		<fo:root font-family="DejaVuSans">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="Main"
									   page-height="29.7cm" page-width="21cm" margin-top="1.8cm"
									   margin-bottom="1cm" margin-left="2cm" margin-right="2cm">
					<fo:region-body/>
				</fo:simple-page-master>
			</fo:layout-master-set>


			<fo:page-sequence master-reference="Main">
				<fo:flow flow-name="xsl-region-body">
					<fo:block font-size="34pt" border="solid"
							  text-align="center" space-after="10pt">
						<xsl:for-each select="song">
							<xsl:value-of select="title"/>
						</xsl:for-each>
					</fo:block>
					<fo:table table-layout="fixed" width="17cm">
						<fo:table-column column-width="10cm"/>
						<fo:table-column column-width="7cm"/>
						<fo:table-body>
							<xsl:for-each select="song">
								<fo:table-row>
									<fo:table-cell>
										<fo:block linefeed-treatment="preserve">
											<xsl:value-of select="lyrics"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>
									<fo:table-cell>
										<fo:block>
											<fo:leader/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</xsl:for-each>
						</fo:table-body>
					</fo:table>


				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>


</xsl:stylesheet>
