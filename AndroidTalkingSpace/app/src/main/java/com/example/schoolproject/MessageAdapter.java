package com.example.schoolproject;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{


    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase,mUserDatabase1;

    public MessageAdapter(List<Messages> mMessageList) {

        this.mMessageList = mMessageList;

    }
    View v;
    Messages c;
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout ,parent, false);

        return new MessageViewHolder(v);

    }
    String from_user;
    String message_type;
    String userid=FirebaseAuth.getInstance().getUid();
    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText,timetext;
        public CircleImageView profileImage;
        public TextView displayName;


        public MessageViewHolder(View view) {
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_layout);
            profileImage = (CircleImageView) view.findViewById(R.id.message_profile_layout);
            displayName = (TextView) view.findViewById(R.id.name_text_layout);
            timetext=view.findViewById(R.id.time_text_layout);



        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {

         c = mMessageList.get(i);

       from_user = c.getFrom();
         message_type = c.getType();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);
       /// mUserDatabase1=FirebaseDatabase.getInstance().getReference().child("messages").child(t)

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                viewHolder.displayName.setText("From "+name);


                Picasso.get().load(image)
                        .placeholder(R.drawable.default_avatar).into(viewHolder.profileImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(message_type.equals("text")) {

            viewHolder.messageText.setText(c.getMessage());
            //viewHolder.messageImage.setVisibility(View.INVISIBLE);


        } else {
//
//            viewHolder.messageText.setVisibility(View.INVISIBLE);
//            Picasso.get().load(c.getMessage())
//                    .placeholder(R.drawable.default_avatar).into(viewHolder.messageImage);
        }

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }






}
