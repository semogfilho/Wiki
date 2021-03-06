package adamatti.web

import groovy.util.logging.Slf4j

import javax.annotation.PostConstruct

import org.springframework.stereotype.Component

import spark.Request
import spark.Response
import spark.Spark

@Slf4j
@Component
class StaticView {
	@PostConstruct
	void init(){
		//TODO find a better place, it shouldn't be here
		Spark.get("favicon.ico") { Request req, Response res ->
			String path = "src/main/webapp/favicon.ico"
			return serveFile(path,req,res)
		}
	}

	private def serveFile(String path, Request req, Response res){
		this.addCacheHeaders(res)

		//InputStream inputStream = getClass().getResourceAsStream(path)
		def file = new File(path)
		if (!file.exists()){
			log.warn("File not found: " + path)
			return null
		}
		InputStream inputStream = new FileInputStream(file)

		if (inputStream != null) {
			res.status(200)

			byte[] buf = new byte[1024]
			OutputStream os = res.raw().getOutputStream()
			OutputStreamWriter outWriter = new OutputStreamWriter(os)
			int count = 0
			while ((count = inputStream.read(buf)) >= 0) {
				os.write(buf, 0, count)
			}
			inputStream.close()
			outWriter.close()

			return ""
		}
		return null

	}

	private void addCacheHeaders(Response res){
		res.header("Cache-Control","public, max-age=14400")
		//res.header("Content-Encoding","gzip")
		def dtFormat = "EEE, dd MMM yyyy HH:mm:ss zzz"
		res.header("Expires", (new Date()+1).format(dtFormat))
		res.header("Last-Modified",(new Date()-1).format(dtFormat))
	}
}
