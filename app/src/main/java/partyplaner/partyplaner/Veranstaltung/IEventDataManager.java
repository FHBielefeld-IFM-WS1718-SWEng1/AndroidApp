package partyplaner.partyplaner.Veranstaltung;

import partyplaner.data.party.Guest;
import partyplaner.data.party.Party;
import partyplaner.data.party.Task;

/**
 * Created by malte on 09.01.2018.
 */

public interface IEventDataManager {

    public String[] getGeneralInformations();

    public Party getParty();

    public void startLoading();
}
