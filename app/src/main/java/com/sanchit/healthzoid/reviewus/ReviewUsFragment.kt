package com.sanchit.healthzoid.reviewus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.sanchit.healthzoid.R
import com.sanchit.healthzoid.databinding.FragmentReviewUsBinding
import java.lang.Exception

class ReviewUsFragment : Fragment() {
    private lateinit var binding:FragmentReviewUsBinding
    private lateinit var viewModelFactory: ReviewUsViewModelFactory
    private val viewModel:ReviewUsViewModel by lazy{
        ViewModelProvider(this,viewModelFactory).get(ReviewUsViewModel::class.java)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Write a review"
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_review_us, container, false)
        val application = requireNotNull(this.activity).application
        viewModelFactory = ReviewUsViewModelFactory(application)
        val sharedPreferences = application.applicationContext.getSharedPreferences("ReviewDetails",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(),gso)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.nameEditText.setText(sharedPreferences.getString("name",""))
        binding.reviewEditText.setText(sharedPreferences.getString("review",""))
        binding.ratingBar.rating = sharedPreferences.getFloat("rating",3f)

        binding.reviewEditText.addTextChangedListener{
            viewModel.store(it!!.length)
            if(it.length==250)
                Toast.makeText(context,"Maximum limit reached",Toast.LENGTH_SHORT).show()
        }

        binding.doneButton.setOnClickListener {
            Log.i("Username - " + binding.nameEditText.text.toString(),binding.reviewEditText.text.toString() + " " + binding.ratingBar.rating)
            editor.putString("name",binding.nameEditText.text.toString())
            editor.putString("review",binding.reviewEditText.text.toString())
            editor.putFloat("rating",binding.ratingBar.rating)
            editor.putInt("numOfWords",binding.reviewEditText.text!!.length)
            editor.apply()
            val user = auth.currentUser
            updateUI(user)
            this.findNavController().navigate(ReviewUsFragmentDirections.actionReviewUsFragmentToDailyDietFragment())
        }

        binding.signInButton.setOnClickListener {
            signIn()
        }

        return binding.root
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.i(TAG,"firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.i(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        val database: FirebaseFirestore? = FirebaseFirestore.getInstance()
        try {
            val review = mutableMapOf<String,Any>()
            review.put("name", binding.nameEditText.text.toString().trim())
            review.put("reviewtext", binding.reviewEditText.text.toString().trim())
            review.put("rating", binding.ratingBar.rating)
            val doc = database?.collection("users")?.document(auth.uid.toString())
            doc!!.set(review).addOnSuccessListener {
                Toast.makeText(context, "Review submitted", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Log.i(TAG,it.toString())
                Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
            }
        }catch (e: Exception){
            Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
            Log.i(TAG,e.toString())
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context,"Sign in successful",Toast.LENGTH_SHORT).show()
                    Log.i(TAG, "signInWithCredential:success")
//                    val user = auth.currentUser
//                    updateUI(user)
                } else {
                    Log.i(TAG, "signInWithCredential:failure", task.exception)
                    Snackbar.make(requireView(), "Authentication Failed.", Snackbar.LENGTH_SHORT)
                        .show()
//                    updateUI(null)
                }
            }
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}