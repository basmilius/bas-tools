package com.basmilius.bastools.framework.all.completion

import com.basmilius.bastools.core.util.FileUtils
import com.basmilius.bastools.core.util.PhpUtils
import com.basmilius.bastools.framework.base.completion.BaseCompletionProvider
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext
import com.jetbrains.php.lang.PhpLanguage
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.parser.PhpElementTypes

/**
 * Class BasicFunctionCompletionProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.completion
 * @since 1.0.0
 */
class BasicFunctionCompletionProvider: BaseCompletionProvider()
{

	private val _caretMagicIdentifier = "IntellijIdeaRulezzz"

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, results: CompletionResultSet)
	{
		val functionName = PhpUtils.getCanonicalFunctionName(parameters.position.parent.parent.parent) ?: return
		val project = parameters.position.project
		var resultElements: Array<String> = arrayOf()
		var resultInfos: Array<String> = arrayOf()
		var resultPostfix = ""
		var resultPostfixAlt = ""
		var resultPostfixExceptions: Array<String> = arrayOf()
		var resultBold = false
		var resultCaseSensitivity = true

		val paramIndex = PhpUtils.getParameterIndex(parameters.position.parent)

		if (functionName.isEmpty())
			return

		val stringLiteral = parameters.position.text
		var stringPrefix = ""

		if (stringLiteral.contains(_caretMagicIdentifier))
			stringPrefix = stringLiteral.substring(0, stringLiteral.indexOf(_caretMagicIdentifier))

		if (methodMatches(functionName, paramIndex, CompletionTokens.FileOtherFunctions))
		{
			resultElements = FileUtils.getRelativeFiles(parameters.position.containingFile.originalFile)
		}

		if (methodMatches(functionName, paramIndex, CompletionTokens.FileFunctions))
		{
			resultElements = CompletionTokens.FileFunctionModes
			resultInfos = CompletionTokens.FileFunctionInfos
			resultBold = true
		}

		if (methodMatchesAt(functionName, paramIndex, CompletionTokens.HttpHeaderFunctions, 0))
		{
			if (stringPrefix.startsWith("Allow:"))
			{
				resultElements = CompletionTokens.HttpHeaderMethods
			}
			else if (stringPrefix.startsWith("Accept-Ranges:") || stringPrefix.startsWith("Content-Range:"))
			{
				resultElements = CompletionTokens.HttpHeaderRangeTypes
			}
			else if (stringPrefix.startsWith("Cache-Control:"))
			{
				resultElements = CompletionTokens.HttpHeaderCacheControlDirectives
			}
			else if (stringPrefix.startsWith("Connection:"))
			{
				resultElements = CompletionTokens.HttpHeaderConnectionOptions
			}
			else if (stringPrefix.startsWith("Content-Encoding:"))
			{
				resultElements = CompletionTokens.HttpHeaderEncodingTokens
				resultCaseSensitivity = false
			}
			else if (stringPrefix.startsWith("Content-Language:"))
			{
				resultElements = CompletionTokens.HttpHeaderLanguageCodes
			}
			else if (stringPrefix.startsWith("Content-Location:") || stringPrefix.startsWith("Location:"))
			{
				resultElements = prefixArray("/", FileUtils.getProjectFiles(project))
				resultElements = concatArrays(arrayOf("/"), resultElements)
			}
			else if (stringPrefix.startsWith("Content-Disposition:"))
			{
				resultElements = CompletionTokens.HttpHeaderContentDispositionTokens
			}
			else if (stringPrefix.startsWith("Content-Type:") && !stringPrefix.contains(";"))
			{
				resultElements = CompletionTokens.HttpHeaderMimeTypes
			}
			else if (stringPrefix.startsWith("Content-Type:") && stringPrefix.trim().endsWith(";"))
			{
				resultElements = prefixArray("charset=", CompletionTokens.HttpHeaderCharsets)
				resultCaseSensitivity = false
			}
			else if (stringPrefix.startsWith("Content-Type:") && stringPrefix.trim().endsWith("charset="))
			{
				resultElements = CompletionTokens.HttpHeaderCharsets
				resultCaseSensitivity = false
			}
			else if (stringPrefix.startsWith("Pragma:"))
			{
				resultElements = CompletionTokens.HttpHeaderPragmaDirectives
			}
			else if (stringPrefix.startsWith("Proxy-Authenticate") || stringPrefix.startsWith("WWW-Authenticate"))
			{
				resultElements = CompletionTokens.HttpHeaderAuthenticationTypes
			}
			else if (stringPrefix.startsWith("Status:") || stringPrefix.startsWith("HTTP/1.0") || stringPrefix.startsWith("HTTP/1.1") || stringPrefix.startsWith("HTTP/2.0"))
			{
				resultElements = CompletionTokens.HttpHeaderStatusCodes

				if (stringPrefix.startsWith("HTTP/1.1") || stringPrefix.startsWith("HTTP/2.0"))
				{
					resultElements = concatArrays(resultElements, CompletionTokens.HttpHeaderStatusCodes11)
				}
			}
			else if (stringPrefix.startsWith("Trailer:"))
			{
				resultElements = CompletionTokens.HttpHeaderRequestFields
			}
			else if (stringPrefix.startsWith("Transfer-Encoding:"))
			{
				resultElements = CompletionTokens.HttpHeaderTransferEncodingValues
			}
			else if (stringPrefix.startsWith("Vary:"))
			{
				resultElements = concatArrays(arrayOf("*"), CompletionTokens.HttpHeaderRequestFields)
			}
			else if (stringPrefix.startsWith("X-Frame-Options:"))
			{
				resultElements = CompletionTokens.HttpHeaderXFrameOptions
			}
			else if (stringPrefix.startsWith("Content-Security-Policy:") || stringPrefix.startsWith("X-Content-Security-Policy:") || stringPrefix.startsWith("X-WebKit-CSP:"))
			{
				resultElements = CompletionTokens.HttpHeaderCSP
			}
			else if (stringPrefix.startsWith("X-Content-Type-Options:"))
			{
				resultElements = CompletionTokens.HttpHeaderXContentTypeOptions
			}
			else if (stringPrefix.startsWith("X-UA-Compatible:"))
			{
				resultElements = CompletionTokens.HttpHeaderXUACompatibleValues
			}
			else if (stringPrefix.startsWith("X-Robots-Tag:"))
			{
				resultElements = CompletionTokens.HttpHeaderXRobotsTagDirectives
			}
			else if (!stringPrefix.contains(":"))
			{
				resultElements = CompletionTokens.HttpHeaderResponseFields
				resultPostfix = ":"
				resultPostfixAlt = " "
				resultPostfixExceptions = arrayOf("HTTP/1.0", "HTTP/1.1", "HTTP/2.0")
			}
		}

		if (resultElements.isEmpty())
			return

		for (i in 0 until resultElements.size)
		{
			val postfix = if (resultPostfixExceptions.contains(resultElements[i])) resultPostfixAlt else resultPostfix
			val builder = LookupElementBuilder.create(resultElements[i] + postfix)
					.withCaseSensitivity(resultCaseSensitivity)
					.withPresentableText(resultElements[i])
					.withBoldness(resultBold)
					.withLookupString(resultElements[i].toLowerCase())

			if (resultInfos.isNotEmpty())
				builder.withTypeText(resultInfos[i])

			results.addElement(builder)
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getPlace(): ElementPattern<out PsiElement>
	{
		val stringInFunctionCall = PlatformPatterns.psiElement(PhpElementTypes.STRING)
				.withParent(PlatformPatterns.psiElement(PhpElementTypes.PARAMETER_LIST)
						.withParent(PlatformPatterns.or(
								PlatformPatterns.psiElement(PhpElementTypes.FUNCTION_CALL),
								PlatformPatterns.psiElement(PhpElementTypes.METHOD_REFERENCE),
								PlatformPatterns.psiElement(PhpElementTypes.NEW_EXPRESSION)
						))
				)

		return PlatformPatterns.or(
				PlatformPatterns.psiElement(PhpTokenTypes.STRING_LITERAL).withParent(stringInFunctionCall).withLanguage(PhpLanguage.INSTANCE),
				PlatformPatterns.psiElement(PhpTokenTypes.STRING_LITERAL_SINGLE_QUOTE).withParent(stringInFunctionCall).withLanguage(PhpLanguage.INSTANCE)
		)
	}

	/**
	 * Concats two arrays.
	 *
	 * @param a Array<String>
	 * @param b Array<String>
	 *
	 * @return Array<String>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	private fun concatArrays(a: Array<String>, b: Array<String>): Array<String>
	{
		val c = ArrayList<String>()

		c.addAll(a)
		c.addAll(b)

		return c.toTypedArray()
	}

	/**
	 * Prefixes a String Array.
	 *
	 * @param prefix String
	 * @param a Array<String>
	 *
	 * @return Array<String>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	private fun prefixArray(prefix: String, a: Array<String>): Array<String>
	{
		val b = ArrayList<String>()

		for (i in 0..(a.size - 1))
		{
			b.add(i, prefix + a[i])
		}

		return b.toTypedArray()
	}

	/**
	 * Returns TRUE if the method matches our expectations.
	 *
	 * @param methodName String
	 * @param paramIndex Int
	 * @param tokens Array<String>
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	private fun methodMatches(methodName: String, paramIndex: Int, tokens: Array<String>) = tokens.firstOrNull { it.startsWith("$methodName:$paramIndex") } != null

	/**
	 * Returns TRUE if the method matches our expextations.
	 *
	 * @param methodName String
	 * @param paramIndex Int
	 * @param tokens Array<String>
	 * @param expectedParamIndex Int
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	private fun methodMatchesAt(methodName: String, paramIndex: Int, tokens: Array<String>, expectedParamIndex: Int) = tokens.contains(methodName) && paramIndex == expectedParamIndex

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun isAvailable() = true

	/**
	 * Class CompletionTokens
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.framework.all.completion.BasicFunctionCompletionProvider
	 * @since 1.0.0
	 */
	object CompletionTokens
	{

		val FileFunctions = arrayOf("fopen:1", "popen:1", "SplFileInfo::openFile:0")
		val FileFunctionModes = arrayOf("r", "r+", "w", "w+", "a", "a+", "x", "x+", "c", "c+")
		val FileFunctionInfos = arrayOf("Read from beginning", "Read/Write from beginning", "Create/Overwrite from beginning", "Create/Overwrite/Read from beginning", "Create/Append at end", "Create/Append/Read at end", "Create/Write from beginning (fails if file exists)", "Create/Write/Read from beginning (fails is file exists)", "Create/Write from beginning", "Create/Write/Read from beginning")
		val FileOtherFunctions = arrayOf("basename:0", "chgrp:0", "chmod:0", "chown:0", "clearstatcache:1", "copy:0", "copy:1", "dirname:0", "file_exists:0", "file_get_contents:0:f", "file_put_contents:0:f", "file:0:f", "fileatime:0", "filectime:0", "filegroup:0", "fileinode:0", "filemtime:0", "fileowner:0", "fileperms:0", "filesize:0:f", "filetype:0:f", "fopen:0:f", "is_dir:0:d", "is_executable:0:f", "is_file:0:f", "is_link:0", "is_readable:0:f", "is_writable:0:f", "is_writeable:0:f", "lchgrp:0", "lchown:0", "link:0", "link:1", "linkinfo:0", "lstat:0", "move_uploaded_file:1:f", "parse_ini_file:0:f", "pathinfo:0", "readfile:0:f", "readlink:0", "realpath:0", "rename:0", "rename:1", "stat:0", "symlink:0", "symlink:1", "touch:0", "unlink:0:f", "dir:0:d", "chdir:0:d", "chroot:0:d", "mkdir:0:d", "rmdir:0:d", "opendir:0:d", "scandir:0:d", "stream_resolve_include_path:0", "SplFileInfo::__construct:0", "SplFileObject::__construct:0", "DirectoryIterator::__construct:0:d", "FilesystemIterator::__construct:0:d", "RecursiveDirectoryIterator::__construct:0:d", "ZipArchive::open:0:f", "ZipArchive::addFile:0:f", "DOMDocument::load:0:f", "simplexml_load_file:0:f", "SimpleXMLElement::asXML:0:f", "SimpleXMLElement::saveXML:0:f", "XMLWriter::openURI:0:f", "xmlwriter_open_uri:0:f", "XMLReader::open:0:f", "imagecreatefromjpeg:0:f", "ImageCreateFromJpeg:0:f", "imagecreatefrompng:0:f", "ImageCreateFromPng:0:f", "imagecreatefromgif:0:f", "ImageCreateFromGif:0:f")

		val HttpHeaderFunctions = arrayOf("header", "header_remove")
		val HttpHeaderResponseFields = arrayOf("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Access-Control-Allow-Headers", "Access-Control-Allow-Methods", "Access-Control-Expose-Headers", "Access-Control-Max-Age", "Accept-Patch", "Accept-Ranges", "Age", "Allow", "Cache-Control", "Connection", "Content-Disposition", "Content-Encoding", "Content-Language", "Content-Length", "Content-Location", "Content-MD5", "Content-Range", "Content-Type", "Date", "ETag", "Expires", "Last-Modified", "Link", "Location", "Pragma", "Proxy-Authenticate", "Public-Key-Pins", "Retry-After", "Server", "Set-Cookie", "Strict-Transport-Policy", "Trailer", "Transfer-Encoding", "Tk", "Upgrade", "Vary", "Via", "Warning", "WWW-Authenticate", "X-Frame-Options", "Content-Security-Policy", "X-Content-Security-Policy", "X-WebKit-CSP", "Refresh", "Status", "Upgrade-Insecure-Requests", "X-Content-Duration", "X-Content-Type-Options", "X-Powered-By", "X-Request-ID", "X-Correlation-ID", "X-UA-Compatible", "X-XSS-Protection")
		val HttpHeaderMethods = arrayOf("GET", "POST", "HEAD", "PUT", "DELETE", "PATCH", "TRACE", "OPTIONS", "CONNECT")
		val HttpHeaderRangeTypes = arrayOf("bytes", "none")
		val HttpHeaderCacheControlDirectives = arrayOf("public", "private", "no-cache", "no-store", "no-transform", "must-revalidate", "proxy-revalidate", "max-age", "s-maxage")
		val HttpHeaderConnectionOptions = arrayOf("keep-alive", "close")
		val HttpHeaderEncodingTokens = arrayOf("compress", "deflare", "exi", "gzip", "identity", "pack200-gzip", "sdch", "bzip2", "peerdist", "lzma")
		val HttpHeaderLanguageCodes = arrayOf("aa", "ab", "af", "am", "ar-AE", "ar-BH", "ar-DZ", "ar-EG", "ar-IQ", "ar-JO", "ar-KW", "ar-LB", "ar-LY", "ar-MA", "ar-OM", "ar-QA", "ar-SA", "ar-SY", "ar-TN", "ar-YE", "ar", "as", "ay", "az", "ba", "be", "bg", "bh", "bi", "bn", "bo", "br", "ca", "cel-gaulish", "co", "cs", "cy", "da", "de-AT", "de-CH", "de-DE", "de-LI", "de-LU", "de", "div", "dz", "el", "en-AU", "en-BZ", "en-CA", "en-GB", "en-IE", "en-JM", "en-NZ", "en-PH", "en-TT", "en-US", "en-ZA", "en-ZW", "en", "eo", "es-AR", "es-BO", "es-CL", "es-CO", "es-CR", "es-DO", "es-EC", "es-ES", "es-GT", "es-HN", "es-MX", "es-NI", "es-PA", "es-PE", "es-PR", "es-PY", "es-SV", "es-US", "es-UY", "es-VE", "es", "et", "eu", "fa", "fi", "fj", "fo", "fr-BE", "fr-CA", "fr-CH", "fr-FR", "fr-LU", "fr-MC", "fr", "fy", "ga", "gd", "gl", "gn", "gu", "ha", "he", "hi", "hr", "hu", "hy", "ia", "id", "ie", "ik", "is", "it-CH", "it-IT", "it", "iu", "iw", "ja", "ji", "jv", "ka", "kk", "kl", "km", "kn", "ko", "kok", "ks", "ku", "ky", "kz", "la", "ln", "lo", "lt", "lv", "mg", "mi", "mk", "ml", "mn", "mo", "mr", "ms", "mt", "my", "na", "nb-NO", "ne", "nl-BE", "nl-NL", "nl", "nn-NO", "no-NO", "no", "oc", "om", "or", "pa", "pl", "ps", "pt-BR", "pt-PT", "pt", "qu", "rm", "rn", "ro-MD", "ro-RO", "ro", "ru-MD", "ru-RU", "ru", "rw", "sa", "sb", "sd", "sg", "sh", "si", "sk", "sl", "sm", "sn", "so", "sq", "sr", "ss", "st", "su", "sv-FI", "sv-SE", "sv", "sw", "sx", "syr", "ta", "te", "tg", "th", "ti", "tk", "tl", "tn", "to", "tr", "ts", "tt", "tw", "ug", "uk", "ur", "uz", "vi", "vo", "wo", "xh", "yi", "yo", "za", "zh-CN", "zh-HK", "zh-MO", "zh-SG", "zh-TW", "zh-guoyu", "zh-min-nan", "zh-xiang", "zh", "zu", "x-elmer", "x-hacker", "x-klingon", "x-pig-latin", "x-pirate")
		val HttpHeaderContentDispositionTokens = arrayOf("attachment; filename=")
		val HttpHeaderMimeTypes = arrayOf("application/1d-interleaved-parityfec", "application/3gpp-ims+xml", "application/activemessage", "application/andrew-inset", "application/applefile", "application/atom+xml", "application/atomdeleted+xml", "application/atomicmail", "application/atomcat+xml", "application/atomsvc+xml", "application/auth-policy+xml", "application/batch-SMTP", "application/beep+xml", "application/calendar+xml", "application/call-completion", "application/cals-1840", "application/ccmp+xml", "application/ccxml+xml", "application/cdmi-capability", "application/cdmi-container", "application/cdmi-domain", "application/cdmi-object", "application/cdmi-queue", "application/cea-2018+xml", "application/cellml+xml", "application/cfw", "application/cnrp+xml", "application/commonground", "application/conference-info+xml", "application/cpl+xml", "application/csta+xml", "application/CSTAdata+xml", "application/cybercash", "application/dash+xml", "application/davmount+xml", "application/dca-rft", "application/dec-dx", "application/dialog-info+xml", "application/dicom", "application/dns", "application/dskpp+xml", "application/dssc+der", "application/dssc+xml", "application/dvcs", "application/ecmascript", "application/EDI-Consent", "application/EDIFACT", "application/EDI-X12", "application/emma+xml", "application/encaprtp", "application/epp+xml", "application/eshop", "application/example", "application/exi", "application/fastinfoset", "application/fastsoap", "application/fdt+xml", "application/fits", "application/font-sfnt", "application/font-tdpfr", "application/font-woff", "application/framework-attributes+xml", "application/gzip", "application/H224", "application/held+xml", "application/http", "application/hyperstudio", "application/ibe-key-request+xml", "application/ibe-pkg-reply+xml", "application/ibe-pp-data", "application/iges", "application/im-iscomposing+xml", "application/index", "application/index.cmd", "application/index.obj", "application/index.response", "application/index.vnd", "application/inkml+xml", "application/iotp", "application/ipfix", "application/ipp", "application/isup", "application/javascript", "application/json", "application/json-patch+json", "application/kpml-request+xml", "application/kpml-response+xml", "application/link-format", "application/lost+xml", "application/lostsync+xml", "application/mac-binhex40", "application/macwriteii", "application/mads+xml", "application/marc", "application/marcxml+xml", "application/mathematica", "application/mathml-content+xml", "application/mathml-presentation+xml", "application/mathml+xml", "application/mbms-associated-procedure-description+xml", "application/mbms-deregister+xml", "application/mbms-envelope+xml", "application/mbms-msk-response+xml", "application/mbms-msk+xml", "application/mbms-protection-description+xml", "application/mbms-reception-report+xml", "application/mbms-register-response+xml", "application/mbms-register+xml", "application/mbms-user-service-description+xml", "application/mbox", "application/media_control+xml", "application/media-policy-dataset+xml", "application/mediaservercontrol+xml", "application/metalink4+xml", "application/mets+xml", "application/mikey", "application/mods+xml", "application/moss-keys", "application/moss-signature", "application/mosskey-data", "application/mosskey-request", "application/mp21", "application/mp4", "application/mpeg4-generic", "application/mpeg4-iod", "application/mpeg4-iod-xmt", "application/mrb-consumer+xml", "application/mrb-publish+xml", "application/msc-ivr+xml", "application/msc-mixer+xml", "application/msword", "application/mxf", "application/nasdata", "application/news-checkgroups", "application/news-groupinfo", "application/news-transmission", "application/nlsml+xml", "application/nss", "application/ocsp-request", "application/ocsp-response", "application/octet-stream", "application/oda", "application/oebps-package+xml", "application/ogg", "application/oxps", "application/p2p-overlay+xml", "application/parityfec", "application/patch-ops-error+xml", "application/pdf", "application/pgp-encrypted", "application/pgp-keys", "application/pgp-signature", "application/pidf+xml", "application/pidf-diff+xml", "application/pkcs10", "application/pkcs7-mime", "application/pkcs7-signature", "application/pkcs8", "application/pkix-attr-cert", "application/pkix-cert", "application/pkixcmp", "application/pkix-crl", "application/pkix-pkipath", "application/pls+xml", "application/poc-settings+xml", "application/postscript", "application/provenance+xml", "application/prs.alvestrand.titrax-sheet", "application/prs.cww", "application/prs.nprend", "application/prs.plucker", "application/prs.rdf-xml-crypt", "application/prs.xsf+xml", "application/pskc+xml", "application/rdf+xml", "application/qsig", "application/raptorfec", "application/reginfo+xml", "application/relax-ng-compact-syntax", "application/remote-printing", "application/resource-lists-diff+xml", "application/resource-lists+xml", "application/riscos", "application/rlmi+xml", "application/rls-services+xml", "application/rpki-ghostbusters", "application/rpki-manifest", "application/rpki-roa", "application/rpki-updown", "application/rtf", "application/rtploopback", "application/rtx", "application/samlassertion+xml", "application/samlmetadata+xml", "application/sbml+xml", "application/scvp-cv-request", "application/scvp-cv-response", "application/scvp-vp-request", "application/scvp-vp-response", "application/sdp", "application/sep-exi", "application/sep+xml", "application/session-info", "application/set-payment", "application/set-payment-initiation", "application/set-registration", "application/set-registration-initiation", "application/sgml", "application/sgml-open-catalog", "application/shf+xml", "application/sieve", "application/simple-filter+xml", "application/simple-message-summary", "application/simpleSymbolContainer", "application/slate", "application/smil (OBSOLETE)", "application/smil+xml", "application/smpte336m", "application/soap+fastinfoset", "application/soap+xml", "application/sparql-query", "application/sparql-results+xml", "application/spirits-event+xml", "application/sql", "application/srgs", "application/srgs+xml", "application/sru+xml", "application/ssml+xml", "application/tamp-apex-update", "application/tamp-apex-update-confirm", "application/tamp-community-update", "application/tamp-community-update-confirm", "application/tamp-error", "application/tamp-sequence-adjust", "application/tamp-sequence-adjust-confirm", "application/tamp-status-query", "application/tamp-status-response", "application/tamp-update", "application/tamp-update-confirm", "application/tei+xml", "application/thraud+xml", "application/timestamp-query", "application/timestamp-reply", "application/timestamped-data", "application/tve-trigger", "application/ulpfec", "application/urc-grpsheet+xml", "application/urc-ressheet+xml", "application/urc-targetdesc+xml", "application/urc-uisocketdesc+xml", "application/vcard+xml", "application/vemmi", "application/vnd.3gpp.bsf+xml", "application/vnd.3gpp.pic-bw-large", "application/vnd.3gpp.pic-bw-small", "application/vnd.3gpp.pic-bw-var", "application/vnd.3gpp.sms", "application/vnd.3gpp2.bcmcsinfo+xml", "application/vnd.3gpp2.sms", "application/vnd.3gpp2.tcap", "application/vnd.3M.Post-it-Notes", "application/vnd.accpac.simply.aso", "application/vnd.accpac.simply.imp", "application/vnd.acucobol", "application/vnd.acucorp", "application/vnd.adobe.formscentral.fcdt", "application/vnd.adobe.fxp", "application/vnd.adobe.partial-upload", "application/vnd.adobe.xdp+xml", "application/vnd.adobe.xfdf", "application/vnd.aether.imp", "application/vnd.ah-barcode", "application/vnd.ahead.space", "application/vnd.airzip.filesecure.azf", "application/vnd.airzip.filesecure.azs", "application/vnd.americandynamics.acc", "application/vnd.amiga.ami", "application/vnd.amundsen.maze+xml", "application/vnd.anser-web-certificate-issue-initiation", "application/vnd.antix.game-component", "application/vnd.apple.mpegurl", "application/vnd.apple.installer+xml", "application/vnd.aristanetworks.swi", "application/vnd.astraea-software.iota", "application/vnd.audiograph", "application/vnd.autopackage", "application/vnd.avistar+xml", "application/vnd.balsamiq.bmml+xml", "application/vnd.blueice.multipass", "application/vnd.bluetooth.ep.oob", "application/vnd.bmi", "application/vnd.businessobjects", "application/vnd.cab-jscript", "application/vnd.canon-cpdl", "application/vnd.canon-lips", "application/vnd.cendio.thinlinc.clientconf", "application/vnd.century-systems.tcp_stream", "application/vnd.chemdraw+xml", "application/vnd.chipnuts.karaoke-mmd", "application/vnd.cinderella", "application/vnd.cirpack.isdn-ext", "application/vnd.claymore", "application/vnd.cloanto.rp9", "application/vnd.clonk.c4group", "application/vnd.cluetrust.cartomobile-config", "application/vnd.cluetrust.cartomobile-config-pkg", "application/vnd.collection+json", "application/vnd.collection.next+json", "application/vnd.commerce-battelle", "application/vnd.commonspace", "application/vnd.cosmocaller", "application/vnd.contact.cmsg", "application/vnd.crick.clicker", "application/vnd.crick.clicker.keyboard", "application/vnd.crick.clicker.palette", "application/vnd.crick.clicker.template", "application/vnd.crick.clicker.wordbank", "application/vnd.criticaltools.wbs+xml", "application/vnd.ctc-posml", "application/vnd.ctct.ws+xml", "application/vnd.cups-pdf", "application/vnd.cups-postscript", "application/vnd.cups-ppd", "application/vnd.cups-raster", "application/vnd.cups-raw", "application/vnd.curl", "application/vnd.cyan.dean.root+xml", "application/vnd.cybank", "application/vnd.dart", "application/vnd.data-vision.rdz", "application/vnd.dece.data", "application/vnd.dece.ttml+xml", "application/vnd.dece.unspecified", "application/vnd.dece.zip", "application/vnd.denovo.fcselayout-link", "application/vnd.desmume.movie", "application/vnd.dir-bi.plate-dl-nosuffix", "application/vnd.dm.delegation+xml", "application/vnd.dna", "application/vnd.dolby.mobile.1", "application/vnd.dolby.mobile.2", "application/vnd.dpgraph", "application/vnd.dreamfactory", "application/vnd.dtg.local", "application/vnd.dtg.local.flash", "application/vnd.dtg.local.html", "application/vnd.dvb.ait", "application/vnd.dvb.dvbj", "application/vnd.dvb.esgcontainer", "application/vnd.dvb.ipdcdftnotifaccess", "application/vnd.dvb.ipdcesgaccess", "application/vnd.dvb.ipdcesgaccess2", "application/vnd.dvb.ipdcesgpdd", "application/vnd.dvb.ipdcroaming", "application/vnd.dvb.iptv.alfec-base", "application/vnd.dvb.iptv.alfec-enhancement", "application/vnd.dvb.notif-aggregate-root+xml", "application/vnd.dvb.notif-container+xml", "application/vnd.dvb.notif-generic+xml", "application/vnd.dvb.notif-ia-msglist+xml", "application/vnd.dvb.notif-ia-registration-request+xml", "application/vnd.dvb.notif-ia-registration-response+xml", "application/vnd.dvb.notif-init+xml", "application/vnd.dvb.pfr", "application/vnd.dvb.service", "application/vnd.dxr", "application/vnd.dynageo", "application/vnd.easykaraoke.cdgdownload", "application/vnd.ecdis-update", "application/vnd.ecowin.chart", "application/vnd.ecowin.filerequest", "application/vnd.ecowin.fileupdate", "application/vnd.ecowin.series", "application/vnd.ecowin.seriesrequest", "application/vnd.ecowin.seriesupdate", "application/vnd.emclient.accessrequest+xml", "application/vnd.enliven", "application/vnd.eprints.data+xml", "application/vnd.epson.esf", "application/vnd.epson.msf", "application/vnd.epson.quickanime", "application/vnd.epson.salt", "application/vnd.epson.ssf", "application/vnd.ericsson.quickcall", "application/vnd.eszigno3+xml", "application/vnd.etsi.aoc+xml", "application/vnd.etsi.cug+xml", "application/vnd.etsi.iptvcommand+xml", "application/vnd.etsi.iptvdiscovery+xml", "application/vnd.etsi.iptvprofile+xml", "application/vnd.etsi.iptvsad-bc+xml", "application/vnd.etsi.iptvsad-cod+xml", "application/vnd.etsi.iptvsad-npvr+xml", "application/vnd.etsi.iptvservice+xml", "application/vnd.etsi.iptvsync+xml", "application/vnd.etsi.iptvueprofile+xml", "application/vnd.etsi.mcid+xml", "application/vnd.etsi.mheg5", "application/vnd.etsi.overload-control-policy-dataset+xml", "application/vnd.etsi.pstn+xml", "application/vnd.etsi.sci+xml", "application/vnd.etsi.simservs+xml", "application/vnd.etsi.tsl+xml", "application/vnd.etsi.tsl.der", "application/vnd.eudora.data", "application/vnd.ezpix-album", "application/vnd.ezpix-package", "application/vnd.f-secure.mobile", "application/vnd.fdf", "application/vnd.fdsn.mseed", "application/vnd.fdsn.seed", "application/vnd.ffsns", "application/vnd.fints", "application/vnd.FloGraphIt", "application/vnd.fluxtime.clip", "application/vnd.font-fontforge-sfd", "application/vnd.framemaker", "application/vnd.frogans.fnc", "application/vnd.frogans.ltf", "application/vnd.fsc.weblaunch", "application/vnd.fujitsu.oasys", "application/vnd.fujitsu.oasys2", "application/vnd.fujitsu.oasys3", "application/vnd.fujitsu.oasysgp", "application/vnd.fujitsu.oasysprs", "application/vnd.fujixerox.ART4", "application/vnd.fujixerox.ART-EX", "application/vnd.fujixerox.ddd", "application/vnd.fujixerox.docuworks", "application/vnd.fujixerox.docuworks.binder", "application/vnd.fujixerox.docuworks.container", "application/vnd.fujixerox.HBPL", "application/vnd.fut-misnet", "application/vnd.fuzzysheet", "application/vnd.genomatix.tuxedo", "application/vnd.geogebra.file", "application/vnd.geogebra.tool", "application/vnd.geometry-explorer", "application/vnd.geonext", "application/vnd.geoplan", "application/vnd.geospace", "application/vnd.globalplatform.card-content-mgt", "application/vnd.globalplatform.card-content-mgt-response", "application/vnd.google-earth.kml+xml", "application/vnd.google-earth.kmz", "application/vnd.grafeq", "application/vnd.gridmp", "application/vnd.groove-account", "application/vnd.groove-help", "application/vnd.groove-identity-message", "application/vnd.groove-injector", "application/vnd.groove-tool-message", "application/vnd.groove-tool-template", "application/vnd.groove-vcard", "application/vnd.hal+json", "application/vnd.hal+xml", "application/vnd.HandHeld-Entertainment+xml", "application/vnd.hbci", "application/vnd.hcl-bireports", "application/vnd.hhe.lesson-player", "application/vnd.hp-HPGL", "application/vnd.hp-hpid", "application/vnd.hp-hps", "application/vnd.hp-jlyt", "application/vnd.hp-PCL", "application/vnd.hp-PCLXL", "application/vnd.httphone", "application/vnd.hydrostatix.sof-data", "application/vnd.hzn-3d-crossword", "application/vnd.ibm.afplinedata", "application/vnd.ibm.electronic-media", "application/vnd.ibm.MiniPay", "application/vnd.ibm.modcap", "application/vnd.ibm.rights-management", "application/vnd.ibm.secure-container", "application/vnd.iccprofile", "application/vnd.ieee.1905", "application/vnd.igloader", "application/vnd.immervision-ivp", "application/vnd.immervision-ivu", "application/vnd.informedcontrol.rms+xml", "application/vnd.infotech.project", "application/vnd.infotech.project+xml", "application/vnd.informix-visionary", "application/vnd.innopath.wamp.notification", "application/vnd.insors.igm", "application/vnd.intercon.formnet", "application/vnd.intergeo", "application/vnd.intertrust.digibox", "application/vnd.intertrust.nncp", "application/vnd.intu.qbo", "application/vnd.intu.qfx", "application/vnd.iptc.g2.conceptitem+xml", "application/vnd.iptc.g2.knowledgeitem+xml", "application/vnd.iptc.g2.newsitem+xml", "application/vnd.iptc.g2.newsmessage+xml", "application/vnd.iptc.g2.packageitem+xml", "application/vnd.iptc.g2.planningitem+xml", "application/vnd.ipunplugged.rcprofile", "application/vnd.irepository.package+xml", "application/vnd.is-xpr", "application/vnd.isac.fcs", "application/vnd.jam", "application/vnd.japannet-directory-service", "application/vnd.japannet-jpnstore-wakeup", "application/vnd.japannet-payment-wakeup", "application/vnd.japannet-registration", "application/vnd.japannet-registration-wakeup", "application/vnd.japannet-setstore-wakeup", "application/vnd.japannet-verification", "application/vnd.japannet-verification-wakeup", "application/vnd.jcp.javame.midlet-rms", "application/vnd.jisp", "application/vnd.joost.joda-archive", "application/vnd.jsk.isdn-ngn", "application/vnd.kahootz", "application/vnd.kde.karbon", "application/vnd.kde.kchart", "application/vnd.kde.kformula", "application/vnd.kde.kivio", "application/vnd.kde.kontour", "application/vnd.kde.kpresenter", "application/vnd.kde.kspread", "application/vnd.kde.kword", "application/vnd.kenameaapp", "application/vnd.kidspiration", "application/vnd.Kinar", "application/vnd.koan", "application/vnd.kodak-descriptor", "application/vnd.las.las+xml", "application/vnd.liberty-request+xml", "application/vnd.llamagraphics.life-balance.desktop", "application/vnd.llamagraphics.life-balance.exchange+xml", "application/vnd.lotus-1-2-3", "application/vnd.lotus-approach", "application/vnd.lotus-freelance", "application/vnd.lotus-notes", "application/vnd.lotus-organizer", "application/vnd.lotus-screencam", "application/vnd.lotus-wordpro", "application/vnd.macports.portpkg", "application/vnd.marlin.drm.actiontoken+xml", "application/vnd.marlin.drm.conftoken+xml", "application/vnd.marlin.drm.license+xml", "application/vnd.marlin.drm.mdcf", "application/vnd.mcd", "application/vnd.medcalcdata", "application/vnd.mediastation.cdkey", "application/vnd.meridian-slingshot", "application/vnd.MFER", "application/vnd.mfmp", "application/vnd.micrografx.flo", "application/vnd.micrografx.igx", "application/vnd.mif", "application/vnd.minisoft-hp3000-save", "application/vnd.mitsubishi.misty-guard.trustweb", "application/vnd.Mobius.DAF", "application/vnd.Mobius.DIS", "application/vnd.Mobius.MBK", "application/vnd.Mobius.MQY", "application/vnd.Mobius.MSL", "application/vnd.Mobius.PLC", "application/vnd.Mobius.TXF", "application/vnd.mophun.application", "application/vnd.mophun.certificate", "application/vnd.motorola.flexsuite", "application/vnd.motorola.flexsuite.adsi", "application/vnd.motorola.flexsuite.fis", "application/vnd.motorola.flexsuite.gotap", "application/vnd.motorola.flexsuite.kmr", "application/vnd.motorola.flexsuite.ttc", "application/vnd.motorola.flexsuite.wem", "application/vnd.motorola.iprm", "application/vnd.mozilla.xul+xml", "application/vnd.ms-artgalry", "application/vnd.ms-asf", "application/vnd.ms-cab-compressed", "application/vnd.mseq", "application/vnd.ms-excel", "application/vnd.ms-excel.addin.macroEnabled.12", "application/vnd.ms-excel.sheet.binary.macroEnabled.12", "application/vnd.ms-excel.sheet.macroEnabled.12", "application/vnd.ms-excel.template.macroEnabled.12", "application/vnd.ms-fontobject", "application/vnd.ms-htmlhelp", "application/vnd.ms-ims", "application/vnd.ms-lrm", "application/vnd.ms-office.activeX+xml", "application/vnd.ms-officetheme", "application/vnd.ms-playready.initiator+xml", "application/vnd.ms-powerpoint", "application/vnd.ms-powerpoint.addin.macroEnabled.12", "application/vnd.ms-powerpoint.presentation.macroEnabled.12", "application/vnd.ms-powerpoint.slide.macroEnabled.12", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12", "application/vnd.ms-powerpoint.template.macroEnabled.12", "application/vnd.ms-project", "application/vnd.ms-tnef", "application/vnd.ms-windows.printerpairing", "application/vnd.ms-wmdrm.lic-chlg-req", "application/vnd.ms-wmdrm.lic-resp", "application/vnd.ms-wmdrm.meter-chlg-req", "application/vnd.ms-wmdrm.meter-resp", "application/vnd.ms-word.document.macroEnabled.12", "application/vnd.ms-word.template.macroEnabled.12", "application/vnd.ms-works", "application/vnd.ms-wpl", "application/vnd.ms-xpsdocument", "application/vnd.msign", "application/vnd.multiad.creator", "application/vnd.multiad.creator.cif", "application/vnd.musician", "application/vnd.music-niff", "application/vnd.muvee.style", "application/vnd.mynfc", "application/vnd.ncd.control", "application/vnd.ncd.reference", "application/vnd.nervana", "application/vnd.netfpx", "application/vnd.neurolanguage.nlu", "application/vnd.nintendo.nitro.rom", "application/vnd.nitf", "application/vnd.noblenet-directory", "application/vnd.noblenet-sealer", "application/vnd.noblenet-web", "application/vnd.nokia.catalogs", "application/vnd.nokia.conml+wbxml", "application/vnd.nokia.conml+xml", "application/vnd.nokia.iptv.config+xml", "application/vnd.nokia.iSDS-radio-presets", "application/vnd.nokia.landmark+wbxml", "application/vnd.nokia.landmark+xml", "application/vnd.nokia.landmarkcollection+xml", "application/vnd.nokia.ncd", "application/vnd.nokia.n-gage.ac+xml", "application/vnd.nokia.n-gage.data", "application/vnd.nokia.n-gage.symbian.install", "application/vnd.nokia.pcd+wbxml", "application/vnd.nokia.pcd+xml", "application/vnd.nokia.radio-preset", "application/vnd.nokia.radio-presets", "application/vnd.novadigm.EDM", "application/vnd.novadigm.EDX", "application/vnd.novadigm.EXT", "application/vnd.ntt-local.content-share", "application/vnd.ntt-local.file-transfer", "application/vnd.ntt-local.sip-ta_remote", "application/vnd.ntt-local.sip-ta_tcp_stream", "application/vnd.oasis.opendocument.chart", "application/vnd.oasis.opendocument.chart-template", "application/vnd.oasis.opendocument.database", "application/vnd.oasis.opendocument.formula", "application/vnd.oasis.opendocument.formula-template", "application/vnd.oasis.opendocument.graphics", "application/vnd.oasis.opendocument.graphics-template", "application/vnd.oasis.opendocument.image", "application/vnd.oasis.opendocument.image-template", "application/vnd.oasis.opendocument.presentation", "application/vnd.oasis.opendocument.presentation-template", "application/vnd.oasis.opendocument.spreadsheet", "application/vnd.oasis.opendocument.spreadsheet-template", "application/vnd.oasis.opendocument.text", "application/vnd.oasis.opendocument.text-master", "application/vnd.oasis.opendocument.text-template", "application/vnd.oasis.opendocument.text-web", "application/vnd.obn", "application/vnd.oftn.l10n+json", "application/vnd.oipf.contentaccessdownload+xml", "application/vnd.oipf.contentaccessstreaming+xml", "application/vnd.oipf.cspg-hexbinary", "application/vnd.oipf.dae.svg+xml", "application/vnd.oipf.dae.xhtml+xml", "application/vnd.oipf.mippvcontrolmessage+xml", "application/vnd.oipf.pae.gem", "application/vnd.oipf.spdiscovery+xml", "application/vnd.oipf.spdlist+xml", "application/vnd.oipf.ueprofile+xml", "application/vnd.oipf.userprofile+xml", "application/vnd.olpc-sugar", "application/vnd.oma.bcast.associated-procedure-parameter+xml", "application/vnd.oma.bcast.drm-trigger+xml", "application/vnd.oma.bcast.imd+xml", "application/vnd.oma.bcast.ltkm", "application/vnd.oma.bcast.notification+xml", "application/vnd.oma.bcast.provisioningtrigger", "application/vnd.oma.bcast.sgboot", "application/vnd.oma.bcast.sgdd+xml", "application/vnd.oma.bcast.sgdu", "application/vnd.oma.bcast.simple-symbol-container", "application/vnd.oma.bcast.smartcard-trigger+xml", "application/vnd.oma.bcast.sprov+xml", "application/vnd.oma.bcast.stkm", "application/vnd.oma.cab-address-book+xml", "application/vnd.oma.cab-feature-handler+xml", "application/vnd.oma.cab-pcc+xml", "application/vnd.oma.cab-subs-invite+xml", "application/vnd.oma.cab-user-prefs+xml", "application/vnd.oma.dcd", "application/vnd.oma.dcdc", "application/vnd.oma.dd2+xml", "application/vnd.oma.drm.risd+xml", "application/vnd.oma.group-usage-list+xml", "application/vnd.oma.pal+xml", "application/vnd.oma.poc.detailed-progress-report+xml", "application/vnd.oma.poc.final-report+xml", "application/vnd.oma.poc.groups+xml", "application/vnd.oma.poc.invocation-descriptor+xml", "application/vnd.oma.poc.optimized-progress-report+xml", "application/vnd.oma.push", "application/vnd.oma.scidm.messages+xml", "application/vnd.oma.xcap-directory+xml", "application/vnd.omads-email+xml", "application/vnd.omads-file+xml", "application/vnd.omads-folder+xml", "application/vnd.omaloc-supl-init", "application/vnd.oma-scws-config", "application/vnd.oma-scws-http-request", "application/vnd.oma-scws-http-response", "application/vnd.openofficeorg.extension", "application/vnd.openxmlformats-officedocument.custom-properties+xml", "application/vnd.openxmlformats-officedocument.customXmlProperties+xml", "application/vnd.openxmlformats-officedocument.drawing+xml", "application/vnd.openxmlformats-officedocument.drawingml.chart+xml", "application/vnd.openxmlformats-officedocument.drawingml.chartshapes+xml", "application/vnd.openxmlformats-officedocument.drawingml.diagramColors+xml", "application/vnd.openxmlformats-officedocument.drawingml.diagramData+xml", "application/vnd.openxmlformats-officedocument.drawingml.diagramLayout+xml", "application/vnd.openxmlformats-officedocument.drawingml.diagramStyle+xml", "application/vnd.openxmlformats-officedocument.extended-properties+xml", "application/vnd.openxmlformats-officedocument.presentationml.commentAuthors+xml", "application/vnd.openxmlformats-officedocument.presentationml.comments+xml", "application/vnd.openxmlformats-officedocument.presentationml.handoutMaster+xml", "application/vnd.openxmlformats-officedocument.presentationml.notesMaster+xml", "application/vnd.openxmlformats-officedocument.presentationml.notesSlide+xml", "application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/vnd.openxmlformats-officedocument.presentationml.presentation.main+xml", "application/vnd.openxmlformats-officedocument.presentationml.presProps+xml", "application/vnd.openxmlformats-officedocument.presentationml.slide", "application/vnd.openxmlformats-officedocument.presentationml.slide+xml", "application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml", "application/vnd.openxmlformats-officedocument.presentationml.slideMaster+xml", "application/vnd.openxmlformats-officedocument.presentationml.slideshow", "application/vnd.openxmlformats-officedocument.presentationml.slideshow.main+xml", "application/vnd.openxmlformats-officedocument.presentationml.slideUpdateInfo+xml", "application/vnd.openxmlformats-officedocument.presentationml.tableStyles+xml", "application/vnd.openxmlformats-officedocument.presentationml.tags+xml", "application/vnd.openxmlformats-officedocument.presentationml.template", "application/vnd.openxmlformats-officedocument.presentationml.template.main+xml", "application/vnd.openxmlformats-officedocument.presentationml.viewProps+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.calcChain+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.chartsheet+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.comments+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.connections+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.dialogsheet+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.externalLink+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotCacheDefinition+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotCacheRecords+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotTable+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.queryTable+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.revisionHeaders+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.revisionLog+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.sharedStrings+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheetMetadata+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.table+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.tableSingleCells+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.template", "application/vnd.openxmlformats-officedocument.spreadsheetml.template.main+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.userNames+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.volatileDependencies+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml", "application/vnd.openxmlformats-officedocument.theme+xml", "application/vnd.openxmlformats-officedocument.themeOverride+xml", "application/vnd.openxmlformats-officedocument.vmlDrawing", "application/vnd.openxmlformats-officedocument.wordprocessingml.comments+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/vnd.openxmlformats-officedocument.wordprocessingml.document.glossary+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.endnotes+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.footer+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.footnotes+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.numbering+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.template", "application/vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.webSettings+xml", "application/vnd.openxmlformats-package.core-properties+xml", "application/vnd.openxmlformats-package.digital-signature-xmlsignature+xml", "application/vnd.openxmlformats-package.relationships+xml", "application/vnd.orange.indata", "application/vnd.osa.netdeploy", "application/vnd.osgeo.mapguide.package", "application/vnd.osgi.bundle", "application/vnd.osgi.dp", "application/vnd.osgi.subsystem", "application/vnd.otps.ct-kip+xml", "application/vnd.palm", "application/vnd.paos.xml", "application/vnd.pawaafile", "application/vnd.pg.format", "application/vnd.pg.osasli", "application/vnd.piaccess.application-licence", "application/vnd.picsel", "application/vnd.pmi.widget", "application/vnd.poc.group-advertisement+xml", "application/vnd.pocketlearn", "application/vnd.powerbuilder6", "application/vnd.powerbuilder6-s", "application/vnd.powerbuilder7", "application/vnd.powerbuilder75", "application/vnd.powerbuilder75-s", "application/vnd.powerbuilder7-s", "application/vnd.preminet", "application/vnd.previewsystems.box", "application/vnd.proteus.magazine", "application/vnd.publishare-delta-tree", "application/vnd.pvi.ptid1", "application/vnd.pwg-multiplexed", "application/vnd.pwg-xhtml-print+xml", "application/vnd.qualcomm.brew-app-res", "application/vnd.Quark.QuarkXPress", "application/vnd.quobject-quoxdocument", "application/vnd.radisys.moml+xml", "application/vnd.radisys.msml-audit-conf+xml", "application/vnd.radisys.msml-audit-conn+xml", "application/vnd.radisys.msml-audit-dialog+xml", "application/vnd.radisys.msml-audit-stream+xml", "application/vnd.radisys.msml-audit+xml", "application/vnd.radisys.msml-conf+xml", "application/vnd.radisys.msml-dialog-base+xml", "application/vnd.radisys.msml-dialog-fax-detect+xml", "application/vnd.radisys.msml-dialog-fax-sendrecv+xml", "application/vnd.radisys.msml-dialog-group+xml", "application/vnd.radisys.msml-dialog-speech+xml", "application/vnd.radisys.msml-dialog-transform+xml", "application/vnd.radisys.msml-dialog+xml", "application/vnd.radisys.msml+xml", "application/vnd.rainstor.data", "application/vnd.rapid", "application/vnd.realvnc.bed", "application/vnd.recordare.musicxml", "application/vnd.recordare.musicxml+xml", "application/vnd.RenLearn.rlprint", "application/vnd.rig.cryptonote", "application/vnd.route66.link66+xml", "application/vnd.rs-274x", "application/vnd.ruckus.download", "application/vnd.s3sms", "application/vnd.sailingtracker.track", "application/vnd.sbm.cid", "application/vnd.sbm.mid2", "application/vnd.scribus", "application/vnd.sealed.3df", "application/vnd.sealed.csf", "application/vnd.sealed.doc", "application/vnd.sealed.eml", "application/vnd.sealed.mht", "application/vnd.sealed.net", "application/vnd.sealed.ppt", "application/vnd.sealed.tiff", "application/vnd.sealed.xls", "application/vnd.sealedmedia.softseal.html", "application/vnd.sealedmedia.softseal.pdf", "application/vnd.seemail", "application/vnd.sema", "application/vnd.semd", "application/vnd.semf", "application/vnd.shana.informed.formdata", "application/vnd.shana.informed.formtemplate", "application/vnd.shana.informed.interchange", "application/vnd.shana.informed.package", "application/vnd.SimTech-MindMapper", "application/vnd.siren+json", "application/vnd.smaf", "application/vnd.smart.notebook", "application/vnd.smart.teacher", "application/vnd.software602.filler.form+xml", "application/vnd.software602.filler.form-xml-zip", "application/vnd.solent.sdkm+xml", "application/vnd.spotfire.dxp", "application/vnd.spotfire.sfs", "application/vnd.sss-cod", "application/vnd.sss-dtf", "application/vnd.sss-ntf", "application/vnd.stepmania.package", "application/vnd.stepmania.stepchart", "application/vnd.street-stream", "application/vnd.sun.wadl+xml", "application/vnd.sus-calendar", "application/vnd.svd", "application/vnd.swiftview-ics", "application/vnd.syncml.dm.notification", "application/vnd.syncml.dmddf+xml", "application/vnd.syncml.dmtnds+wbxml", "application/vnd.syncml.dmtnds+xml", "application/vnd.syncml.dmddf+wbxml", "application/vnd.syncml.dm+wbxml", "application/vnd.syncml.dm+xml", "application/vnd.syncml.ds.notification", "application/vnd.syncml+xml", "application/vnd.tao.intent-module-archive", "application/vnd.tcpdump.pcap", "application/vnd.tmobile-livetv", "application/vnd.trid.tpt", "application/vnd.triscape.mxs", "application/vnd.trueapp", "application/vnd.truedoc", "application/vnd.ubisoft.webplayer", "application/vnd.ufdl", "application/vnd.uiq.theme", "application/vnd.umajin", "application/vnd.unity", "application/vnd.uoml+xml", "application/vnd.uplanet.alert", "application/vnd.uplanet.alert-wbxml", "application/vnd.uplanet.bearer-choice", "application/vnd.uplanet.bearer-choice-wbxml", "application/vnd.uplanet.cacheop", "application/vnd.uplanet.cacheop-wbxml", "application/vnd.uplanet.channel", "application/vnd.uplanet.channel-wbxml", "application/vnd.uplanet.list", "application/vnd.uplanet.listcmd", "application/vnd.uplanet.listcmd-wbxml", "application/vnd.uplanet.list-wbxml", "application/vnd.uplanet.signal", "application/vnd.vcx", "application/vnd.vd-study", "application/vnd.vectorworks", "application/vnd.verimatrix.vcas", "application/vnd.vidsoft.vidconference", "application/vnd.visio", "application/vnd.visionary", "application/vnd.vividence.scriptfile", "application/vnd.vsf", "application/vnd.wap.sic", "application/vnd.wap.slc", "application/vnd.wap.wbxml", "application/vnd.wap.wmlc", "application/vnd.wap.wmlscriptc", "application/vnd.webturbo", "application/vnd.wfa.wsc", "application/vnd.wmc", "application/vnd.wmf.bootstrap", "application/vnd.wolfram.mathematica", "application/vnd.wolfram.mathematica.package", "application/vnd.wolfram.player", "application/vnd.wordperfect", "application/vnd.wqd", "application/vnd.wrq-hp3000-labelled", "application/vnd.wt.stf", "application/vnd.wv.csp+xml", "application/vnd.wv.csp+wbxml", "application/vnd.wv.ssp+xml", "application/vnd.xacml+json", "application/vnd.xara", "application/vnd.xfdl", "application/vnd.xfdl.webform", "application/vnd.xmi+xml", "application/vnd.xmpie.cpkg", "application/vnd.xmpie.dpkg", "application/vnd.xmpie.plan", "application/vnd.xmpie.ppkg", "application/vnd.xmpie.xlim", "application/vnd.yamaha.hv-dic", "application/vnd.yamaha.hv-script", "application/vnd.yamaha.hv-voice", "application/vnd.yamaha.openscoreformat.osfpvg+xml", "application/vnd.yamaha.openscoreformat", "application/vnd.yamaha.remote-setup", "application/vnd.yamaha.smaf-audio", "application/vnd.yamaha.smaf-phrase", "application/vnd.yamaha.through-ngn", "application/vnd.yamaha.tunnel-udpencap", "application/vnd.yellowriver-custom-menu", "application/vnd.zul", "application/vnd.zzazz.deck+xml", "application/voicexml+xml", "application/vq-rtcpxr", "application/watcherinfo+xml", "application/whoispp-query", "application/whoispp-response", "application/widget", "application/wita", "application/wordperfect5.1", "application/wsdl+xml", "application/wspolicy+xml", "application/x400-bp", "application/xcap-att+xml", "application/xcap-caps+xml", "application/xcap-diff+xml", "application/xcap-el+xml", "application/xcap-error+xml", "application/xcap-ns+xml", "application/xcon-conference-info-diff+xml", "application/xcon-conference-info+xml", "application/xenc+xml", "application/xhtml+xml", "application/xml", "application/xml-dtd", "application/xml-external-parsed-entity", "application/xmpp+xml", "application/xop+xml", "application/xslt+xml", "application/xv+xml", "application/yang", "application/yin+xml", "application/zip", "application/zlib", "audio/1d-interleaved-parityfec", "audio/32kadpcm", "audio/3gpp", "audio/3gpp2", "audio/ac3", "audio/AMR", "audio/AMR-WB", "audio/amr-wb+", "audio/asc", "audio/ATRAC-ADVANCED-LOSSLESS", "audio/ATRAC-X", "audio/ATRAC3", "audio/basic", "audio/BV16", "audio/BV32", "audio/clearmode", "audio/CN", "audio/DAT12", "audio/dls", "audio/dsr-es201108", "audio/dsr-es202050", "audio/dsr-es202211", "audio/dsr-es202212", "audio/DV", "audio/DVI4", "audio/eac3", "audio/encaprtp", "audio/EVRC", "audio/EVRC-QCP", "audio/EVRC0", "audio/EVRC1", "audio/EVRCB", "audio/EVRCB0", "audio/EVRCB1", "audio/EVRCNW", "audio/EVRCNW0", "audio/EVRCNW1", "audio/EVRCWB", "audio/EVRCWB0", "audio/EVRCWB1", "audio/example", "audio/fwdred", "audio/G719", "audio/G722", "audio/G7221", "audio/G723", "audio/G726-16", "audio/G726-24", "audio/G726-32", "audio/G726-40", "audio/G728", "audio/G729", "audio/G7291", "audio/G729D", "audio/G729E", "audio/GSM", "audio/GSM-EFR", "audio/GSM-HR-08", "audio/iLBC", "audio/ip-mr_v2.5", "audio/L8", "audio/L16", "audio/L20", "audio/L24", "audio/LPC", "audio/mobile-xmf", "audio/MPA", "audio/mp4", "audio/MP4A-LATM", "audio/mpa-robust", "audio/mpeg", "audio/mpeg4-generic", "audio/ogg", "audio/parityfec", "audio/PCMA", "audio/PCMA-WB", "audio/PCMU", "audio/PCMU-WB", "audio/prs.sid", "audio/QCELP", "audio/raptorfec", "audio/RED", "audio/rtp-enc-aescm128", "audio/rtploopback", "audio/rtp-midi", "audio/rtx", "audio/SMV", "audio/SMV0", "audio/SMV-QCP", "audio/sp-midi", "audio/speex", "audio/t140c", "audio/t38", "audio/telephone-event", "audio/tone", "audio/UEMCLIP", "audio/ulpfec", "audio/VDVI", "audio/VMR-WB", "audio/vnd.3gpp.iufp", "audio/vnd.4SB", "audio/vnd.audiokoz", "audio/vnd.CELP", "audio/vnd.cisco.nse", "audio/vnd.cmles.radio-events", "audio/vnd.cns.anp1", "audio/vnd.cns.inf1", "audio/vnd.dece.audio", "audio/vnd.digital-winds", "audio/vnd.dlna.adts", "audio/vnd.dolby.heaac.1", "audio/vnd.dolby.heaac.2", "audio/vnd.dolby.mlp", "audio/vnd.dolby.mps", "audio/vnd.dolby.pl2", "audio/vnd.dolby.pl2x", "audio/vnd.dolby.pl2z", "audio/vnd.dolby.pulse.1", "audio/vnd.dra", "audio/vnd.dts", "audio/vnd.dts.hd", "audio/vnd.dvb.file", "audio/vnd.everad.plj", "audio/vnd.hns.audio", "audio/vnd.lucent.voice", "audio/vnd.ms-playready.media.pya", "audio/vnd.nokia.mobile-xmf", "audio/vnd.nortel.vbk", "audio/vnd.nuera.ecelp4800", "audio/vnd.nuera.ecelp7470", "audio/vnd.nuera.ecelp9600", "audio/vnd.octel.sbc", "audio/vnd.rhetorex.32kadpcm", "audio/vnd.rip", "audio/vnd.sealedmedia.softseal.mpeg", "audio/vnd.vmx.cvsd", "audio/vorbis", "audio/vorbis-config", "image/cgm", "image/example", "image/fits", "image/g3fax", "image/gif", "image/ief", "image/jp2", "image/jpeg", "image/jpm", "image/jpx", "image/ktx", "image/naplps", "image/png", "image/prs.btif", "image/prs.pti", "image/pwg-raster", "image/svg+xml", "image/t38", "image/tiff", "image/tiff-fx", "image/vnd.adobe.photoshop", "image/vnd.airzip.accelerator.azv", "image/vnd.cns.inf2", "image/vnd.dece.graphic", "image/vnd.djvu", "image/vnd.dwg", "image/vnd.dxf", "image/vnd.dvb.subtitle", "image/vnd.fastbidsheet", "image/vnd.fpx", "image/vnd.fst", "image/vnd.fujixerox.edmics-mmr", "image/vnd.fujixerox.edmics-rlc", "image/vnd.globalgraphics.pgb", "image/vnd.microsoft.icon", "image/vnd.mix", "image/vnd.ms-modi", "image/vnd.net-fpx", "image/vnd.radiance", "image/vnd.sealed.png", "image/vnd.sealedmedia.softseal.gif", "image/vnd.sealedmedia.softseal.jpg", "image/vnd.svf", "image/vnd.wap.wbmp", "image/vnd.xiff", "message/CPIM", "message/delivery-status", "message/disposition-notification", "message/example", "message/external-body", "message/feedback-report", "message/global", "message/global-delivery-status", "message/global-disposition-notification", "message/global-headers", "message/http", "message/imdn+xml", "message/partial", "message/rfc822", "message/s-http", "message/sip", "message/sipfrag", "message/tracking-status", "model/example", "model/iges", "model/mesh", "model/vnd.collada+xml", "model/vnd.dwf", "model/vnd.flatland.3dml", "model/vnd.gdl", "model/vnd.gs-gdl", "model/vnd.gtw", "model/vnd.moml+xml", "model/vnd.mts", "model/vnd.parasolid.transmit.binary", "model/vnd.parasolid.transmit.text", "model/vnd.vtu", "model/vrml", "multipart/alternative", "multipart/appledouble", "multipart/byteranges", "multipart/digest", "multipart/encrypted", "multipart/example", "multipart/form-data", "multipart/header-set", "multipart/mixed", "multipart/parallel", "multipart/related", "multipart/report", "multipart/signed", "multipart/voice-message", "text/1d-interleaved-parityfec", "text/calendar", "text/css", "text/csv", "text/dns", "text/encaprtp", "text/enriched", "text/example", "text/fwdred", "text/grammar-ref-list", "text/html", "text/jcr-cnd", "text/mizar", "text/n3", "text/parityfec", "text/plain", "text/provenance-notation", "text/prs.fallenstein.rst", "text/prs.lines.tag", "text/raptorfec", "text/RED", "text/rfc822-headers", "text/richtext", "text/rtf", "text/rtp-enc-aescm128", "text/rtploopback", "text/rtx", "text/sgml", "text/t140", "text/tab-separated-values", "text/troff", "text/turtle", "text/ulpfec", "text/uri-list", "text/vcard", "text/vnd.abc", "text/vnd.curl", "text/vnd.debian.copyright", "text/vnd.DMClientScript", "text/vnd.dvb.subtitle", "text/vnd.esmertec.theme-descriptor", "text/vnd.fly", "text/vnd.fmi.flexstor", "text/vnd.graphviz", "text/vnd.in3d.3dml", "text/vnd.in3d.spot", "text/vnd.IPTC.NewsML", "text/vnd.IPTC.NITF", "text/vnd.latex-z", "text/vnd.motorola.reflex", "text/vnd.ms-mediapackage", "text/vnd.net2phone.commcenter.command", "text/vnd.radisys.msml-basic-layout", "text/vnd.sun.j2me.app-descriptor", "text/vnd.trolltech.linguist", "text/vnd.wap.si", "text/vnd.wap.sl", "text/vnd.wap.wml", "text/vnd.wap.wmlscript", "text/xml", "text/xml-external-parsed-entity", "video/1d-interleaved-parityfec", "video/3gpp", "video/3gpp2", "video/3gpp-tt", "video/BMPEG", "video/BT656", "video/CelB", "video/DV", "video/encaprtp", "video/example", "video/H261", "video/H263", "video/H263-1998", "video/H263-2000", "video/H264", "video/H264-RCDO", "video/H264-SVC", "video/JPEG", "video/jpeg2000", "video/MJ2", "video/MP1S", "video/MP2P", "video/MP2T", "video/mp4", "video/MP4V-ES", "video/MPV", "video/mpeg", "video/mpeg4-generic", "video/nv", "video/ogg", "video/parityfec", "video/pointer", "video/quicktime", "video/raptorfec", "video/raw", "video/rtp-enc-aescm128", "video/rtploopback", "video/rtx", "video/SMPTE292M", "video/ulpfec", "video/vc1", "video/vnd.CCTV", "video/vnd.dece.hd", "video/vnd.dece.mobile", "video/vnd.dece.mp4", "video/vnd.dece.pd", "video/vnd.dece.sd", "video/vnd.dece.video", "video/vnd.directv.mpeg", "video/vnd.directv.mpeg-tts", "video/vnd.dlna.mpeg-tts", "video/vnd.dvb.file", "video/vnd.fvt", "video/vnd.hns.video", "video/vnd.iptvforum.1dparityfec-1010", "video/vnd.iptvforum.1dparityfec-2005", "video/vnd.iptvforum.2dparityfec-1010", "video/vnd.iptvforum.2dparityfec-2005", "video/vnd.iptvforum.ttsavc", "video/vnd.iptvforum.ttsmpeg2", "video/vnd.motorola.video", "video/vnd.motorola.videop", "video/vnd.mpegurl", "video/vnd.ms-playready.media.pyv", "video/vnd.nokia.interleaved-multimedia", "video/vnd.nokia.videovoip", "video/vnd.objectvideo", "video/vnd.radgamettools.bink", "video/vnd.radgamettools.smacker", "video/vnd.sealed.mpeg1", "video/vnd.sealed.mpeg4", "video/vnd.sealed.swf", "video/vnd.sealedmedia.softseal.mov", "video/vnd.uvvu.mp4", "video/vnd.vivo")
		val HttpHeaderCharsets = arrayOf("Adobe-Standard-Encoding", "Adobe-Symbol-Encoding", "Amiga-1251", "ANSI_X3.110-1983", "ASMO_449", "Big5", "Big5-HKSCS", "BOCU-1", "BRF", "BS_4730", "BS_viewdata", "CESU-8", "CP50220", "CP51932", "CSA_Z243.4-1985-1", "CSA_Z243.4-1985-2", "CSA_Z243.4-1985-gr", "CSN_369103", "DEC-MCS", "DIN_66003", "dk-us", "DS_2089", "EBCDIC-AT-DE", "EBCDIC-AT-DE-A", "EBCDIC-CA-FR", "EBCDIC-DK-NO", "EBCDIC-DK-NO-A", "EBCDIC-ES", "EBCDIC-ES-A", "EBCDIC-ES-S", "EBCDIC-FI-SE", "EBCDIC-FI-SE-A", "EBCDIC-FR", "EBCDIC-IT", "EBCDIC-PT", "EBCDIC-UK", "EBCDIC-US", "ECMA-cyrillic", "ES", "ES2", "EUC-JP", "EUC-KR", "GB18030", "GB2312", "GB_1988-80", "GB_2312-80", "GBK", "GOST_19768-74", "greek-ccitt", "greek7", "greek7-old", "HP-DeskTop", "HP-Legal", "HP-Math8", "HP-Pi-font", "hp-roman8", "HZ-GB-2312", "IBM-Symbols", "IBM-Thai", "IBM00858", "IBM00924", "IBM01140", "IBM01141", "IBM01142", "IBM01143", "IBM01144", "IBM01145", "IBM01146", "IBM01147", "IBM01148", "IBM01149", "IBM037", "IBM038", "IBM1026", "IBM1047", "IBM273", "IBM274", "IBM275", "IBM277", "IBM278", "IBM280", "IBM281", "IBM284", "IBM285", "IBM290", "IBM297", "IBM420", "IBM423", "IBM424", "IBM437", "IBM500", "IBM775", "IBM850", "IBM851", "IBM852", "IBM855", "IBM857", "IBM860", "IBM861", "IBM862", "IBM863", "IBM864", "IBM865", "IBM866", "IBM868", "IBM869", "IBM870", "IBM871", "IBM880", "IBM891", "IBM903", "IBM904", "IBM905", "IBM918", "IEC_P27-1", "INIS", "INIS-8", "INIS-cyrillic", "INVARIANT", "ISO-10646-J-1", "ISO-10646-UCS-2", "ISO-10646-UCS-4", "ISO-10646-UCS-Basic", "ISO-10646-Unicode-Latin1", "ISO-10646-UTF-1", "ISO-11548-1", "ISO-2022-CN", "ISO-2022-CN-EXT", "ISO-2022-JP", "ISO-2022-JP-2", "ISO-2022-KR", "ISO-8859-1", "ISO-8859-1-Windows-3.0-Latin-1", "ISO-8859-1-Windows-3.1-Latin-1", "ISO-8859-10", "ISO-8859-13", "ISO-8859-14", "ISO-8859-15", "ISO-8859-16", "ISO-8859-2", "ISO-8859-2-Windows-Latin-2", "ISO-8859-3", "ISO-8859-4", "ISO-8859-5", "ISO-8859-6", "ISO-8859-6-E", "ISO-8859-6-I", "ISO-8859-7", "ISO-8859-8", "ISO-8859-8-E", "ISO-8859-8-I", "ISO-8859-9", "ISO-8859-9-Windows-Latin-5", "ISO-8859-supp", "iso-ir-90", "ISO-Unicode-IBM-1261", "ISO-Unicode-IBM-1264", "ISO-Unicode-IBM-1265", "ISO-Unicode-IBM-1268", "ISO-Unicode-IBM-1276", "ISO_10367-box", "ISO_2033-1983", "ISO_5427", "ISO_5427:1981", "ISO_5428:1980", "ISO_646.basic:1983", "ISO_646.irv:1983", "ISO_6937-2-25", "ISO_6937-2-add", "IT", "JIS_C6220-1969-jp", "JIS_C6220-1969-ro", "JIS_C6226-1978", "JIS_C6226-1983", "JIS_C6229-1984-a", "JIS_C6229-1984-b", "JIS_C6229-1984-b-add", "JIS_C6229-1984-hand", "JIS_C6229-1984-hand-add", "JIS_C6229-1984-kana", "JIS_Encoding", "JIS_X0201", "JIS_X0212-1990", "JUS_I.B1.002", "JUS_I.B1.003-mac", "JUS_I.B1.003-serb", "KOI7-switched", "KOI8-R", "KOI8-U", "KS_C_5601-1987", "KSC5636", "KZ-1048", "latin-greek", "Latin-greek-1", "latin-lap", "macintosh", "Microsoft-Publishing", "MNEM", "MNEMONIC", "MSZ_7795.3", "NATS-DANO", "NATS-DANO-ADD", "NATS-SEFI", "NATS-SEFI-ADD", "NC_NC00-10:81", "NF_Z_62-010", "NF_Z_62-010_(1973)", "NS_4551-1", "NS_4551-2", "OSD_EBCDIC_DF03_IRV", "OSD_EBCDIC_DF04_1", "OSD_EBCDIC_DF04_15", "PC8-Danish-Norwegian", "PC8-Turkish", "PT", "PT2", "PTCP154", "SCSU", "SEN_850200_B", "SEN_850200_C", "Shift_JIS", "T.101-G2", "T.61-7bit", "T.61-8bit", "TIS-620", "TSCII", "UNICODE-1-1", "UNICODE-1-1-UTF-7", "UNKNOWN-8BIT", "US-ASCII", "us-dk", "UTF-16", "UTF-16BE", "UTF-16LE", "UTF-32", "UTF-32BE", "UTF-32LE", "UTF-7", "UTF-8", "Ventura-International", "Ventura-Math", "Ventura-US", "videotex-suppl", "VIQR", "VISCII", "windows-1250", "windows-1251", "windows-1252", "windows-1253", "windows-1254", "windows-1255", "windows-1256", "windows-1257", "windows-1258", "Windows-31J", "windows-874")
		val HttpHeaderPragmaDirectives = arrayOf("no-cache")
		val HttpHeaderAuthenticationTypes = arrayOf("Basic", "Digest", "NTLM")
		val HttpHeaderStatusCodes = arrayOf("100 Continue", "101 Switching Protocols", "102 Processing", "200 OK", "201 Created", "202 Accepted", "204 No Content", "205 Reset Content", "206 Partial Content", "207 Multi-Status", "208 Already Reported", "226 IM Used", "300 Multiple Choices", "301 Moved Permanently", "302 Found", "304 Not Modified", "306 Switch Proxy", "400 Bad Request", "401 Unauthorized", "402 Payment Required", "403 Forbidden", "404 Not Found", "405 Method Not Allowed", "406 Not Acceptable", "407 Proxy Authentication Required", "408 Request Timeout", "409 Conflict", "410 Gone", "411 Length Required", "412 Precondition Failed", "413 Request Entity Too Large", "414 Request-URI Too Long", "415 Unsupported Media Type", "416 Requested Range Not Satisfiable", "417 Expectation Failed", "419 Authentication Timeout", "422 Unprocessable Entity", "423 Locked", "424 Failed Dependency", "424 Method Failure", "425 Unordered Collection", "426 Upgrade Required", "428 Precondition Required", "429 Too Many Requests", "431 Request Header Fields Too Large", "444 No Response", "449 Retry With", "450 Blocked by Windows Parental Controls", "451 Unavailable For Legal Reasons", "451 Redirect", "494 Request Header Too Large", "495 Cert Error", "496 No Cert", "497 HTTP to HTTPS", "499 Client Closed Request", "500 Internal Server Error", "501 Not Implemented", "502 Bad Gateway", "503 Service Unavailable", "503 Service Temporarily Unavailable", "504 Gateway Timeout", "505 HTTP Version Not Supported", "506 Variant Also Negotiates", "507 Insufficient Storage", "508 Loop Detected", "509 Bandwidth Limit Exceeded", "510 Not Extended", "511 Network Authentication Required", "598 Network read timeout error", "599 Network connect timeout error")
		val HttpHeaderStatusCodes11 = arrayOf("203 Non-Authoritative Information", "303 See Other", "305 Use Proxy", "307 Temporary Redirect", "308 Permanent Redirect")
		val HttpHeaderRequestFields = arrayOf("Accept", "Accept-Charset", "Accept-Datetime", "Accept-Encoding", "Accept-Language", "Authorization", "Cache-Control", "Connection", "Content-Length", "Content-MD5", "Content-Type", "Cookie", "Date", "DNT", "Expect", "From", "Front-End-Https", "Host", "If-Match", "If-Modified-Since", "If-None-Match", "If-Range", "If-Unmodified-Since", "Max-Forwards", "Origin", "Pragma", "Proxy-Authorization", "Proxy-Connection", "Range", "Referer", "TE", "Upgrade", "User-Agent", "Via", "Warning", "X-ATT-DeviceId", "X-Forwarded-For", "X-Forwarded-Proto", "X-Requested-With", "X-Wap-Profile")
		val HttpHeaderTransferEncodingValues = arrayOf("chunked", "compress", "deflate", "gzip", "identity")
		val HttpHeaderXFrameOptions = arrayOf("deny", "same-origin")
		val HttpHeaderXContentTypeOptions = arrayOf("nosniff")
		val HttpHeaderCSP = arrayOf("default-src", "script-src", "object-src", "style-src", "img-src", "media-src", "frame-src", "font-src", "connect-src")
		val HttpHeaderXUACompatibleValues = arrayOf("IE=edge", "IE=7", "IE=8", "IE=9", "IE=10", "IE=11", "IE=EmulateIE7", "IE=EmulateIE8", "IE=EmulateIE9", "IE=EmulateIE10", "IE=EmulateIE11", "Chrome=1")
		val HttpHeaderXRobotsTagDirectives = arrayOf("all", "noindex", "nofollow", "none", "noarchive", "nosnippet", "noodp", "notranslate", "noimageindex", "unavailable_after")

	}

}