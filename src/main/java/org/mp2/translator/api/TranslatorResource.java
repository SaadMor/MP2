package org.mp2.translator.api;

import org.mp2.translator.service.TranslationService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/translator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TranslatorResource {
    private final TranslationService translationService = new TranslationService();

    @POST
    @Path("/translate")
    public Response translate(TranslationRequest request) {
        if (request == null || request.getText() == null || request.getText().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("text is required"))
                    .build();
        }

        String sourceLang = request.getSourceLanguage() == null || request.getSourceLanguage().isBlank()
                ? "English"
                : request.getSourceLanguage();

        TranslationResponse response = translationService.translate(request.getText(), sourceLang, "Darija");
        return Response.ok(response).build();
    }
}
