/*
mediastreamer2 library - modular sound and video processing and streaming
Copyright (C) 2014  Belledonne Communications SARL

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

#ifndef msutils_h
#define msutils_h

#include "mediastreamer2/mscommon.h"


#ifdef __cplusplus
extern "C"{
#endif

typedef void (*MSAudioDiffProgressNotify)(void* user_data, int percentage);
/*utility function to check similarity between two audio wav files*/
MS2_PUBLIC int ms_audio_diff(const char *file1, const char *file2, double *ret, int overlap_per, MSAudioDiffProgressNotify func, void *user_data);

#ifdef __cplusplus
}
#endif

#endif

