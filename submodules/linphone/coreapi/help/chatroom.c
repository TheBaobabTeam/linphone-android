
/*
linphone
Copyright (C) 2010  Belledonne Communications SARL

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

/**
 * @defgroup chatroom_tuto Chat room and messaging
 * @ingroup tutorials
 *This program is a _very_ simple usage example of liblinphone,
 *desmonstrating how to send/receive  SIP MESSAGE from a sip uri identity passed from the command line.
 *<br>Argument must be like sip:jehan@sip.linphone.org .
 *<br>
 *ex chatroom sip:jehan@sip.linphone.org
 *<br>
 *@include chatroom.c

 *
 */

#ifdef IN_LINPHONE
#include "linphonecore.h"
#else
#include "linphone/linphonecore.h"
#endif

#include <signal.h>

static bool_t running=TRUE;

static void stop(int signum){
	running=FALSE;
}
/*void text_received(LinphoneCore *lc, LinphoneChatRoom *room, const LinphoneAddress *from, const char *message) {
	printf(" Message [%s] received from [%s] \n",message,linphone_address_as_string (from));
	linphone_chat_room_print(lc, room, NULL);
}*/

void message_received(LinphoneCore *lc, LinphoneChatRoom *room, LinphoneChatMessage *message) {
	//printf(" Message [%s] received from [%s] \n",message,linphone_address_as_string (from));
	linphone_chat_room_print(lc, room, message);
}


LinphoneCore *lc;
int main(int argc, char *argv[]){
	LinphoneCoreVTable vtable={0};
	
	char* my_id = NULL;
	char* group_name = NULL;
	const char* group_members[argc-3];	// there must be atleast one participant
	int group_size = 0;
	LinphoneChatRoom* chat_room;
	int i;

	time_t rawtime;
	struct tm * timeinfo;
	
	char* nako = NULL;
	char* ke = NULL;
	
	char* nako_ke = NULL;
	
	/* takes   sip uri  identity from the command line arguments */
	if (argc > 1){
		my_id = argv[1];
	}
	
	if (argc > 2) {
		group_name = argv[2];
	}
	
	/* takes   sip uri  identity from the command line arguments */
	if (argc > 3){
		int j;
		group_members[0] = my_id;
		group_size++;
		for (i = 3, j = 1; i < argc; i++, j++) {
			group_members[j] = argv[i];
			group_size++;
		}
	}

	signal(SIGINT,stop);
//#define DEBUG
#ifdef DEBUG
	linphone_core_enable_logs(NULL); /*enable liblinphone logs.*/
#endif
	/*
	 Fill the LinphoneCoreVTable with application callbacks.
	 All are optional. Here we only use the text_received callback
	 in order to get notifications about incoming message.
	 */
	//vtable.text_received = text_received;
	vtable.message_received = message_received;

	/*
	 Instantiate a LinphoneCore object given the LinphoneCoreVTable
	*/
	lc=linphone_core_new(&vtable,NULL,NULL,NULL);
	
	linphone_core_set_primary_contact(lc, my_id);
	
	printf("Group Name: [%s]\n", group_name);
	for (i = 0; i < group_size; i++) {
		printf("Member [%d]: [%s]\n", i, group_members[i]);
	}
	/*Next step is to create a chat root*/
	chat_room = linphone_core_create_group_chat_room(lc, group_name, group_members, group_size, 0, 0);
	
	//linphone_chat_room_add_participant(chat_room, group_member);
	
	if (chat_room != NULL) {
		time(&rawtime);
		timeinfo = localtime(&rawtime);
		
		nako = asctime(timeinfo);
		ke = "The time is: ";
		
		nako_ke = malloc(strlen(ke) + strlen(nako) + 1);
		
		strcpy(nako_ke, ke);
		strcat(nako_ke, nako);
		
		linphone_group_chat_room_send_message(chat_room, nako_ke); /*sending message*/

		/* main loop for receiving incoming messages and doing background linphone core work: */
		while(running){
			linphone_core_iterate(lc);
			ms_usleep(50000);
		}
		
		/*free(nako);*/
		free(nako_ke);
		
		linphone_chat_room_destroy(chat_room);
	} else {
		printf("Could not create chatroom\n");
	}
		
	printf("Shutting down...\n");
	linphone_core_destroy(lc);
	printf("Exited\n");
	return 0;
}
