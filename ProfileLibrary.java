
public class ProfileLibrary {
    private LinkedList<Profile> profiles = new LinkedList<Profile>();

    public boolean addProfile(String username) {
      boolean found = profiles.find(new Profile(username));
      if(found) {
        return false;
      }

      profiles.add(new Profile(username));
      return true;
    }

    public Profile getProfile(String username) {
      Node<Profile> current = profiles.getHead();
      while(current != null) {
        if(current.data.getUsername().equals(username)) {
          return current.data;
        }
        current = current.next;
      }

      return null;
    }
}