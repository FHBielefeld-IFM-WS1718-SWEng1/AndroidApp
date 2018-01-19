package partyplaner.partyplaner;

import partyplaner.data.party.Party;
import partyplaner.data.user.User;

/**
 * Created by malte on 08.01.2018.
 */

public interface IFragmentDataManeger {

    public Party[] getParties();

    public Party[] getOwnParties();

    public boolean partyReceived();

    public User[] getContacts();
 }
