package com.algaworks.highrisehq.managers;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.algaworks.highrisehq.Highrise;
import com.algaworks.highrisehq.bean.Company;
import com.algaworks.highrisehq.bean.Note;
import com.algaworks.highrisehq.bean.Notes;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * 
 * @author thiagofa
 *
 */
public class NoteManager extends HighriseManager {

  public NoteManager(final WebResource webResource, final String authorization) {
    super(webResource, authorization);
  }

  public Note create(final Note note) {
    return this.create(note, Highrise.PEOPLE_NOTES_PATH
        .replaceAll("#\\{person-id\\}", note.getSubjectId().toString()));
  }

  public Note createDealNote(final Note note) {
    return this.create(note, "deals/1524744/notes.xml");
  }

  public Note createCompanyNote(final Note note) {
    return this.create(note, "companies/91239927/notes.xml");
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public List<Note> getAll(final Class subjectType, final Long subjectId) {
    final MultivaluedMap<String, String> params = new MultivaluedMapImpl();

    if (subjectType.isAssignableFrom(Company.class)) {
      return this.getAsList(Note.class, Notes.class, Highrise.COMPANY_NOTES_PATH
          .replaceAll("#\\{subject-id\\}", subjectId.toString()), params);
    } else {
      return this.getAsList(Note.class, Notes.class, Highrise.PEOPLE_NOTES_PATH
          .replaceAll("#\\{person-id\\}", subjectId.toString()), params);
    }
  }

}
