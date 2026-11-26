package com.lyricai.app

import android.os.Bundle
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

private val Background = Color(0xFF090909)
private val Panel = Color(0xFF1B1B1B)
private val Red = Color(0xFFE50914)
private val White = Color.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LyricAIApp()
        }
    }
}

@Composable
fun LyricAIApp() {
    var spotifyLink by remember { mutableStateOf("") }
    var lyrics by remember { mutableStateOf("") }
    var selectedAudio by remember { mutableStateOf<Uri?>(null) }
    var style by remember { mutableStateOf("Cinematográfico") }
    var message by remember {
        mutableStateOf("Seu próximo lyric video começa aqui.")
    }

    val audioPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            selectedAudio = uri
            message = "Áudio selecionado com sucesso!"
        }
    }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Red,
            background = Background,
            surface = Panel
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "LYRIC AI",
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Red
            )

            Text(
                text = "YOUR MUSIC. YOUR VISUAL.",
                fontSize = 11.sp,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "CRIADOR DE LYRIC VIDEOS",
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = spotifyLink,
                onValueChange = { spotifyLink = it },
                label = { Text("Link da música do Spotify") },
                placeholder = { Text("https://open.spotify.com/...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    audioPicker.launch(arrayOf("audio/*"))
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Panel
                )
            ) {
                Text("IMPORTAR ÁUDIO DO CELULAR")
            }

            Text(
                text = selectedAudio?.lastPathSegment
                    ?: "Nenhum áudio selecionado",
                color = Color.LightGray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = lyrics,
                onValueChange = { lyrics = it },
                label = { Text("Insira as letras da música") },
                placeholder = { Text("Digite ou cole suas letras aqui...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "ESTILO VISUAL",
                color = White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            val styles = listOf(
                "Cinematográfico",
                "Rap urbano",
                "Emocional",
                "Minimalista"
            )

            styles.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = style == option,
                        onClick = { style = option },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Red
                        )
                    )
                    Text(option, color = White)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    message = when {
                        selectedAudio == null ->
                            "Primeiro importe um arquivo de áudio."
                        lyrics.isBlank() ->
                            "Insira as letras antes de continuar."
                        else ->
                            "Projeto preparado! A renderização será adicionada na próxima etapa."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "GERAR MEU VÍDEO",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = message,
                color = Color.LightGray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "LYRIC AI • VERSÃO 1.0",
                color = Color.Gray,
                fontSize = 10.sp
            )
        }
    }
}
