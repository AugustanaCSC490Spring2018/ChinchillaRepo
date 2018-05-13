package com.example.chinchillas.chinchillachat;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chinchillas.chinchillachat.datamodel.Message;

import java.util.List;

/**
 * ChatAdapter allows us to display chat messages appropriately.
 *
 * Sources: https://stackoverflow.com/questions/11773369/convert-from-long-to-date-format?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
 * for Date format
 * https://stackoverflow.com/questions/9027317/how-to-convert-milliseconds-to-hhmmss-format?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
 * for time format
 */

public class ChatAdapter extends BaseAdapter {

    private final List<Message> chatMessages;
    private final List<String> friendUsernames; // usernames of other parties in chat
    private Activity context;
    private String usernameForMe;

    /**
     * Constructor. chatMessages should never contain messages with users who are not usernameForMe
     * or in friendUsernames.
     *
     * @param context
     * @param chatMessages list of messages
     * @param usernameForMe my username
     * @param friendUsernames list of usernames of other parties in the chat
     */
    public ChatAdapter(Activity context, List<Message> chatMessages, String usernameForMe, List<String> friendUsernames) {
        this.context = context;
        this.chatMessages = chatMessages;
        this.usernameForMe = usernameForMe;
        this.friendUsernames = friendUsernames;
    }

    /**
     * @return number of messages
     */
    @Override
    public int getCount() {
        if (chatMessages != null) {
            return chatMessages.size();
        } else {
            return 0;
        }
    }

    /**
     * @param position
     * @return message at position
     */
    @Override
    public Message getItem(int position) {
        if (chatMessages != null) {
            return chatMessages.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Message chatMessage = getItem(position);
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = vi.inflate(R.layout.list_item, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String sender = chatMessage.getSenderID();
        //to simulate whether it me or other sender
        setAlignment(holder, sender);
        holder.txtMessage.setText(chatMessage.getMessage());
        holder.txtInfo.setText(sender);
        holder.txtTime.setText(DateFormat.format("MMM dd, yyyy (hh:mm aa)",
                chatMessage.getTime()));
        //above code displays the name of the user who sent the message and the date and time sent in military time
        return convertView;
    }

    /**
     * Add message to list.
     *
     * @param message
     */
    public void add(Message message) {
        chatMessages.add(message);
    }

    /**
     * Add all messages from parameter to list.
     *
     * @param messages
     */
    public void add(List<Message> messages) {
        chatMessages.addAll(messages);
    }

    /**
     * Set alignment and color appropriately based on sender.
     *
     * If sender is this user, the message should be on the right.
     * If sender is not this user, the message should be on the left.
     *
     * @param holder
     * @param sender
     */
    private void setAlignment(ViewHolder holder, String sender) {
        if (!sender.equals(usernameForMe)) {
            int friendNumber = friendUsernames.indexOf(sender);
            int[] out_message_resource_IDs = new int[] { R.drawable.in_message_bglightred,
                    R.drawable.in_message_bgverylightorange, R.drawable.in_message_bglightyellow,
                    R.drawable.in_message_bgsoftgreen, R.drawable.in_message_bgperiwinkle,
                    R.drawable.in_message_bgviolet, R.drawable.in_message_bgpalepurple, //END RAINBOW COLORS
                    R.drawable.in_message_bggreyblue, R.drawable.in_message_bg,
                    R.drawable.in_message_bglightpink, R.drawable.in_message_bgbluegreen,
                    R.drawable.in_message_bghotpink, R.drawable.in_message_bgpwinered,
                    R.drawable.in_message_bggreen };
            holder.contentWithBG.setBackgroundResource(out_message_resource_IDs[friendNumber % out_message_resource_IDs.length]);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtMessage.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtInfo.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtTime.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtTime.setLayoutParams(layoutParams);
        } else {
            holder.contentWithBG.setBackgroundResource(R.drawable.out_message_bg);
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtMessage.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtInfo.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtTime.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtTime.setLayoutParams(layoutParams);
        }
    }

    /**
     * Creates view holder.
     *
     * @param v
     * @return holder
     */
    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.txtMessage = (TextView) v.findViewById(R.id.txtMessage);
        holder.content = (LinearLayout) v.findViewById(R.id.content);
        holder.contentWithBG = (LinearLayout) v.findViewById(R.id.contentWithBG);
        holder.txtTime = (TextView) v.findViewById(R.id.txtTime);
        holder.txtInfo = (TextView) v.findViewById(R.id.txtUser);
        return holder;
    }

    /**
     * Contains important information for displaying messages.
     */
    private static class ViewHolder {
        public TextView txtMessage;
        public TextView txtInfo;
        public TextView txtTime;
        public LinearLayout content;
        public LinearLayout contentWithBG;
    }
}