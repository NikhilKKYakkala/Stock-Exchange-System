import java.util.HashMap;
import java.util.Random;
public class UserManager {
    private HashMap<String, User> users = new HashMap<>();
    private static UserManager instance = null;

    private UserManager(){}

    public static UserManager getInstance() {
        if(instance == null)
            instance = new UserManager();
        return instance;
    }

    public void init(String[] usersIn) throws OrderException {
        for(String user: usersIn) {
            User userobj = new User(user);
            users.put(user, userobj);
        }
    }

    public User getRandomUser() {
        if(!users.isEmpty()) {
            Object[] keyArray = users.keySet().toArray();
            Object key = keyArray[new Random().nextInt(keyArray.length)];
            return users.get(key);
        }
        return null;
    }

    public void addToUser(String userId, OrderDTO o) throws OrderException {
        if(users.containsKey(userId))
            users.get(userId).addOrder(o);
        else
            throw new OrderException("User not found");
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(User user : users.values())
            output.append(user.toString()).append('\n');
        return output.toString();
    }
}

