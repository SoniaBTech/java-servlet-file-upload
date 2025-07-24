@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String destPath = request.getParameter("destination");
        Part filePart = request.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, Paths.get(destPath, fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            response.getWriter().println("Upload failed: " + e.getMessage());
            return;
        }

        response.getWriter().println("File uploaded to: " + destPath + "/" + fileName);
    }
}

