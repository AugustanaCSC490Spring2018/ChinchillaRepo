package com.example.chinchillas.chinchillachat;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chinchillas.chinchillachat.datamodel.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Angelica Garcia on 4/23/2018.
 *
 * Sources: https://stackoverflow.com/questions/11773369/convert-from-long-to-date-format?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
 *          for Date format
 */

public class ChatAdapter extends BaseAdapter {

    private final List<Message> chatMessages;
    private Activity context;
    private String usernameForMe;

    public ChatAdapter(Activity context, List<Message> chatMessages, String usernameForMe) {
        this.context = context;
        this.chatMessages = chatMessages;
        this.usernameForMe = usernameForMe;
    }

    @Override
    public int getCount() {
        if (chatMessages != null) {
            return chatMessages.size();
        } else {
            return 0;
        }
    }

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

        boolean myMsg = chatMessage.getSenderID().equals(usernameForMe);
        //to simulate whether it me or other sender
        setAlignment(holder, myMsg);
        holder.txtMessage.setText(chatMessage.getMessage());
        holder.txtInfo.setText("time: " + String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(chatMessage.getTime()),
                TimeUnit.MILLISECONDS.toMinutes(chatMessage.getTime()) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(chatMessage.getTime())),
                TimeUnit.MILLISECONDS.toSeconds(chatMessage.getTime()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(chatMessage.getTime()))));
       // holder.txtInfo.setText("time: " + new SimpleDateFormat("MM/dd/yyyy").format(new Date(chatMessage.getTime()))); // this displays the date
        // displayed in chat -- "time:" is for debugging only!

        return convertView;
    }

    public void add(Message message) {
        chatMessages.add(message);
    }

    public void add(List<Message> messages) {
        chatMessages.addAll(messages);
    }

    private void setAlignment(ViewHolder holder, boolean isMe) {
        if (!isMe) {
            holder.contentWithBG.setBackgroundResource(R.drawable.out_message_bg);

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
        } else {
            holder.contentWithBG.setBackgroundResource(R.drawable.in_message_bg);
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
        }
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.txtMessage = (TextView) v.findViewById(R.id.txtMessage);
        holder.content = (LinearLayout) v.findViewById(R.id.content);
        holder.contentWithBG = (LinearLayout) v.findViewById(R.id.contentWithBG);
        holder.txtInfo = (TextView) v.findViewById(R.id.txtInfo);
        return holder;
    }

    private static class ViewHolder {
        public TextView txtMessage;
        public TextView txtInfo;
        public LinearLayout content;
        public LinearLayout contentWithBG;
    }
}